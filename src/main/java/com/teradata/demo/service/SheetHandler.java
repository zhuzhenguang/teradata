package com.teradata.demo.service;

import com.teradata.demo.dao.ExcelDao;
import com.teradata.demo.utils.excel.XSSFSheetXMLHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sheet处理器
 *
 * Created by zhu on 14-1-20.
 */
public abstract class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
    private static Logger logger = LoggerFactory.getLogger(SheetHandler.class);
    protected static final int BATCH_SIZE = 1000;

    private ExcelDao excelDao;

    protected boolean available = false;
    protected List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
    protected Map<String, Object> value = null;

    protected SheetHandler(ExcelDao excelDao) {
        this.excelDao = excelDao;
    }

    public static SheetHandler get(String sheetId, ExcelDao excelDao) {
        if ("rId2".equals(sheetId)) {
            return new UserSheetHandler(excelDao);
        } else if ("rId3".equals(sheetId)) {
            return new ProductSheetHandler(excelDao);
        } else if ("rId4".equals(sheetId)) {
            return new SaleSheetHandler(excelDao);
        } else {
            logger.error("没有{}对应的sheet", sheetId);
            return null;
        }
    }

    @Override
    public void endRow() {
        if (!available || StringUtils.isBlank((CharSequence) value.get("businessNo")))
            return;

        if (values.size() < BATCH_SIZE) {
            values.add(value);
        } else {
            getExcelDao().insert(values.toArray(new HashMap[values.size()]));
            values.clear();
        }
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
    }

    @Override
    public void end() {
        excelDao.insert(values.toArray(new HashMap[values.size()]));
    }

    protected ExcelDao getExcelDao() {
        return excelDao;
    }
}

class UserSheetHandler extends SheetHandler {
    private static Map<String, String> COLUMN_MAP = new HashMap<String, String>(5);

    static {
        COLUMN_MAP.put("A", "businessNo");
        COLUMN_MAP.put("B", "name");
        COLUMN_MAP.put("C", "address");
        COLUMN_MAP.put("D", "birthday");
        COLUMN_MAP.put("E", "sex");
    }

    UserSheetHandler(ExcelDao excelDao) {
        super(excelDao);
    }

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
    public void cell(String cellReference, String formattedValue) {
        if (available) {
            System.out.println(cellReference + "-" + formattedValue);
            String index = cellReference.substring(0, 1);
            value.put(COLUMN_MAP.get(index), StringUtils.defaultString(formattedValue));
        }
    }

}

class ProductSheetHandler extends SheetHandler {
    private static Map<String, String> COLUMN_MAP = new HashMap<String, String>(5);

    static {
        COLUMN_MAP.put("A", "businessNo");
        COLUMN_MAP.put("B", "name");
        COLUMN_MAP.put("C", "type");
        COLUMN_MAP.put("D", "sum");
        COLUMN_MAP.put("E", "unit");
    }

    ProductSheetHandler(ExcelDao excelDao) {
        super(excelDao);
    }

    @Override
    public void startRow(int rowNum) {
        available = rowNum != 0;
        if (available) {
            value = new HashMap<String, Object>(5);
            value.put("businessNo", "");
            value.put("name", "");
            value.put("type", "");
            value.put("sum", 0.0);
            value.put("unit", "");
        }
    }

    @Override
    public void cell(String cellReference, String formattedValue) {
        if (available) {
            System.out.println(cellReference + "-" + formattedValue);
            String index = cellReference.substring(0, 1);
            if ("sum".equals(COLUMN_MAP.get(index)) && StringUtils.isNotBlank(formattedValue)) {
                value.put(COLUMN_MAP.get(index), Double.parseDouble(formattedValue));
            } else {
                value.put(COLUMN_MAP.get(index), StringUtils.defaultString(formattedValue));
            }
        }
    }
}

class SaleSheetHandler extends SheetHandler {
    private static Map<String, String> COLUMN_MAP = new HashMap<String, String>(5);

    static {
        COLUMN_MAP.put("A", "saleDate");
        COLUMN_MAP.put("B", "userBusinessNo");
        COLUMN_MAP.put("C", "productBusinessNo");
        COLUMN_MAP.put("D", "sum");
        COLUMN_MAP.put("E", "count");
    }

    SaleSheetHandler(ExcelDao excelDao) {
        super(excelDao);
    }

    @Override
    public void startRow(int rowNum) {
        available = rowNum != 0;
        if (available) {
            value = new HashMap<String, Object>(5);
            value.put(COLUMN_MAP.get("A"), "");
            value.put(COLUMN_MAP.get("B"), "");
            value.put(COLUMN_MAP.get("C"), "");
            value.put(COLUMN_MAP.get("D"), 0.0);
            value.put(COLUMN_MAP.get("E"), 0.0);
        }
    }

    @Override
    public void endRow() {
        if (!available ||
                StringUtils.isBlank((CharSequence) value.get(COLUMN_MAP.get("B"))) ||
                StringUtils.isBlank((CharSequence) value.get(COLUMN_MAP.get("C"))))
            return;

        if (values.size() < BATCH_SIZE) {
            values.add(value);
        } else {
            getExcelDao().insert(values.toArray(new HashMap[values.size()]));
            values.clear();
        }
    }

    @Override
    public void cell(String cellReference, String formattedValue) {
        if (available) {
            System.out.println(cellReference + "-" + formattedValue);
            String index = cellReference.substring(0, 1);
            String property = COLUMN_MAP.get(index);

            if (("sum".equals(property) || "count".equals(property)) && StringUtils.isNotBlank(formattedValue)) {
                value.put(property, Double.parseDouble(formattedValue));
            } else {
                value.put(property, StringUtils.defaultString(formattedValue));
            }
        }
    }
}