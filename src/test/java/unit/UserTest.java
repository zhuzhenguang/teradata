package unit;

import com.google.common.collect.ImmutableMap;
import com.teradata.demo.dao.UserDao;
import com.teradata.demo.utils.config.Application;
import com.teradata.demo.utils.excel.XSSFSheetXMLHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhu on 14-1-19.
 */
public class UserTest {
    private static Logger logger = LoggerFactory.getLogger(UserTest.class);
    private static final String EXCEL = "/Users/zhu/Development/workspace/teradata-demo/src/main/resources/demo.xlsx";
    private static Map<String, String> COLUMN_MAP = new HashMap<String, String>(5);

    static {
        COLUMN_MAP.put("A", "businessNo");
        COLUMN_MAP.put("B", "name");
        COLUMN_MAP.put("C", "address");
        COLUMN_MAP.put("D", "birthday");
        COLUMN_MAP.put("E", "sex");
    }

    private ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

    @Test
    public void test() throws Exception {
        final UserDao userDao = context.getBean(UserDao.class);

        OPCPackage opcPackage = OPCPackage.open(EXCEL, PackageAccess.READ);
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opcPackage);
        XSSFReader xssfReader = new XSSFReader(opcPackage);
        StylesTable styles = xssfReader.getStylesTable();

        XSSFSheetXMLHandler handler = new XSSFSheetXMLHandler(styles, strings, new XSSFSheetXMLHandler.SheetContentsHandler() {
            private static final int BATCH_SIZE = 1000;
            private boolean available = false;
            private List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
            private Map<String, Object> value = null;

            private int insertedRowCount = 0;

            @Override
            public void startRow(int rowNum) {
                available = rowNum != 0;
                if (available) {
                    value = new HashMap<String, Object>(5);
                    value.put("businessNo", "");
                    value.put("name", "");
                    value.put("address", "");
                    value.put("birthday", "");
                    value.put("sex", "");
                }
            }

            @Override
            public void endRow() {
                if (!available || StringUtils.isBlank((CharSequence) value.get("businessNo")))
                    return;

                if (values.size() < BATCH_SIZE) {
                    values.add(value);
                    //insertedRowCount++;
                } else {
                    userDao.insert(values.toArray(new HashMap[values.size()]));
                    //insertedRowCount = 0;
                    values.clear();
                }
            }

            @Override
            public void cell(String cellReference, String formattedValue) {
                if (available) {
                    System.out.println(cellReference + "-" + formattedValue);
                    String index = cellReference.substring(0, 1);
                    value.put(COLUMN_MAP.get(index), StringUtils.defaultString(formattedValue));
                }
            }

            @Override
            public void headerFooter(String text, boolean isHeader, String tagName) {
            }

            @Override
            public void end() {
                userDao.insert(values.toArray(new HashMap[values.size()]));
            }
        }, false);

        XMLReader parser =
                XMLReaderFactory.createXMLReader(
                        "org.apache.xerces.parsers.SAXParser"
                );
        parser.setContentHandler(handler);

        InputStream sheet2 = xssfReader.getSheet("rId2");
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }
}
