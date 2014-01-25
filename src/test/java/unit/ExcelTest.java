package unit;

import com.teradata.demo.service.ExcelService;
import com.teradata.demo.utils.config.Application;
import com.teradata.demo.utils.excel.XSSFSheetXMLHandler;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 测试Excel解析
 * Created by zhu on 14-1-19.
 */
public class ExcelTest {
    private static Logger logger = LoggerFactory.getLogger(ExcelTest.class);
    //private static final String EXCEL = "F:\\self_workspace\\teradata\\src\\main\\resources\\demo.xlsx";
    private static final String EXCEL = "/Users/zhu/Development/workspace/teradata/src/main/resources/demo.xlsx";
    private ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

    @Test
    public void testParse() throws Exception {
        //FromHowTo fromHowTo = new FromHowTo();
        //fromHowTo.processOneSheet("/Users/zhu/Development/workspace/teradata-demo/src/main/resources/demo.xlsx");

        OPCPackage opcPackage = OPCPackage.open("/Users/zhu/Development/workspace/teradata-demo/src/main/resources/test.xlsx", PackageAccess.READ);
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opcPackage);
        XSSFReader xssfReader = new XSSFReader(opcPackage);
        StylesTable styles = xssfReader.getStylesTable();

        XSSFSheetXMLHandler handler = new XSSFSheetXMLHandler(styles, strings, new XSSFSheetXMLHandler.SheetContentsHandler() {
            private boolean available = false;

            @Override
            public void startRow(int rowNum) {
                available = rowNum != 0;
            }

            @Override
            public void endRow() {
            }

            @Override
            public void cell(String cellReference, String formattedValue) {
                if (available) {
                    System.out.println(cellReference + "-" + formattedValue);
                }
            }

            @Override
            public void headerFooter(String text, boolean isHeader, String tagName) {
            }

            @Override
            public void end() {

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

    @Test
    public void testBusinessParse() throws FileNotFoundException {
        ExcelService excelService = context.getBean(ExcelService.class);
        excelService.parse(new FileInputStream(EXCEL));
    }
}
