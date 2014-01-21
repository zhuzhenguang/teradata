package com.teradata.demo.service.impl;

import com.teradata.demo.dao.ExcelDao;
import com.teradata.demo.dao.ProductDao;
import com.teradata.demo.dao.SaleDao;
import com.teradata.demo.dao.UserDao;
import com.teradata.demo.service.ExcelService;
import com.teradata.demo.service.SheetHandler;
import com.teradata.demo.utils.excel.XSSFSheetXMLHandler;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;

/**
 * Excel 服务
 * <p/>
 * Created by zhu on 14-1-20.
 */
public class ExcelServiceImpl implements ExcelService {
    private static final String[] SHEET_IDS = {"rId2", "rId3", "rId4"};
    private static Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

    private UserDao userDao;
    private ProductDao productDao;
    private SaleDao saleDao;

    @Override
    public void parse(InputStream excelStream) {
        OPCPackage opcPackage = null;
        try {
            opcPackage = OPCPackage.open(excelStream);
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opcPackage);
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            StylesTable styles = xssfReader.getStylesTable();

            XMLReader parser =
                    XMLReaderFactory.createXMLReader(
                            "org.apache.xerces.parsers.SAXParser"
                    );
            for (String sheetId : SHEET_IDS) {
                ExcelDao excelDao = getExcelDao(sheetId);
                if (excelDao != null) {
                    XSSFSheetXMLHandler handler = new XSSFSheetXMLHandler(styles, strings,
                            SheetHandler.get(sheetId, excelDao), false);
                    parser.setContentHandler(handler);
                    InputStream sheetStream = xssfReader.getSheet(sheetId);
                    InputSource sheetSource = new InputSource(sheetStream);
                    parser.parse(sheetSource);
                    sheetStream.close();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage());
        }
    }

    private ExcelDao getExcelDao(String sheetId) {
        if ("rId2".equals(sheetId)) {
            return userDao;
        } else if ("rId3".equals(sheetId)) {
            return productDao;
        } else if ("rId4".equals(sheetId)) {
            return saleDao;
        } else {
            return null;
        }
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }
}
