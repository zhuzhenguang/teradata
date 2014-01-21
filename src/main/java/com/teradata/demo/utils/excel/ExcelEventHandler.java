package com.teradata.demo.utils.excel;

import java.util.*;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ExcelEventHandler extends DefaultHandler {
	private static Logger logger = LoggerFactory.getLogger(ExcelEventHandler.class);
	private ExcelBigService excelService;

	/**
	 * Table with styles
	 */
	private StylesTable stylesTable;
	/**
	 * Table with unique strings
	 */
	private ReadOnlySharedStringsTable sharedStringsTable;

	// Set when V start element is seen
	private boolean vIsOpen;

	// Set when cell start element is seen;
	// used when cell close element is seen.
	private XssfDataType nextDataType;

	// Used to format numeric cell values.
	private short formatIndex;
	private String formatString;
	// private final DataFormatter formatter;

	private int thisColumn = -1;
	// The last column printed to the output stream
	private int lastColumnNumber = -1;

	// Gathers characters as they are seen.
	private StringBuffer value;

	private int rowIndex = 0;
	private List<String> headList = new ArrayList<String>();
	private Map<String, String> columnMap;

	Map<String, Object> target;
	private List<Map<String, Object>> targetList = new ArrayList<Map<String, Object>>();
	private Map<String, Object> attributeMap;

	private int maxBatch = 2000;
	private int count = 0;

	public ExcelEventHandler(StylesTable styles, ReadOnlySharedStringsTable strings,
			Map<String, String> columnMap, int maxBatch, ExcelBigService excelService,
			Map<String, Object> attributeMap) {
		this.stylesTable = styles;
		this.sharedStringsTable = strings;
		this.value = new StringBuffer();
		this.nextDataType = XssfDataType.NUMBER;
		// this.formatter = new DataFormatter();
		this.columnMap = columnMap;
		this.maxBatch = maxBatch;
		this.excelService = excelService;
		this.attributeMap = attributeMap;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String thisStr = null;

		// v => contents of a cell
		if ("v".equals(qName)) {
			// Process the value contents as required.
			// Do now, as characters() may be called more than once
			switch (nextDataType) {

			case BOOL:
				char first = value.charAt(0);
				thisStr = first == '0' ? "FALSE" : "TRUE";
				break;

			case ERROR:
				thisStr = "\"ERROR:" + value.toString() + '"';
				break;

			case FORMULA:
				// A formula could result in a string value,
				// so always add double-quote characters.
				thisStr = value.toString();
				break;

			case INLINESTR:
				// TODO: have seen an example of this, so it's untested.
				XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
				thisStr = rtsi.toString();
				break;

			case SSTINDEX:
				String sstIndex = value.toString();
				try {
					int idx = Integer.parseInt(sstIndex);
					XSSFRichTextString rtss = new XSSFRichTextString(
							sharedStringsTable.getEntryAt(idx));
					thisStr = rtss.toString();
				} catch (NumberFormatException ex) {
					logger.error("Failed to parse SST index '{}': {}", sstIndex, ex.toString());
					ex.printStackTrace();
				}
				break;

			case NUMBER:
				String n = value.toString();
				// logger.info(n);
				// /logger.info("{}",this.formatIndex);
				// logger.info("{}",this.formatString);
				/*
				 * if (this.formatString != null) thisStr =
				 * formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex,
				 * this.formatString); else
				 */
				thisStr = n;
				break;

			default:
				thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
				break;
			}

			// 如果是第一行，则加入表头
			if (rowIndex == 0) {
				headList.add(columnMap.get(thisStr.trim()));
				/*
				 * logger.info(thisStr); logger.info(columnMap.get(thisStr));
				 * logger.info("==========================");
				 */
			} else {
				if (thisColumn == 0) {
					// target = Maps.newHashMap();
				}
				// Output after we've seen the string contents
				// Emit commas for any fields that were missing on this row
				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
				for (int i = lastColumnNumber; i < thisColumn; ++i) {
					target.put(headList.get(thisColumn), "");
				}

				target.put(headList.get(thisColumn), thisStr);
				// logger.info("thisColumn:{}", thisColumn);
				// logger.info(headList.get(thisColumn));
				// logger.info("{}", target.get(headList.get(thisColumn)));
				// logger.info("==============================================");

				// Update column
				if (thisColumn > -1) {
					lastColumnNumber = thisColumn;
				}

			}

		} else if ("row".equals(qName)) {// 一行读完了
			if (rowIndex != 0) {
				if (targetList.size() < maxBatch) {
					putAttribute(attributeMap);
				} else {
					excelService.batchInsert(targetList, count);
					count++; // 计数
					targetList.clear();
					putAttribute(attributeMap);
				}
			}
			rowIndex++; // 下一行
			target = new HashMap<String, Object>();
		} else if ("sheetData".equals(qName)) {
			// 到末尾了，插入最后一点
			excelService.batchInsert(targetList, count);
			lastColumnNumber = -1;
		}
	}

	/**
	 * Put attribute.
	 *
	 * @param attributeMap the attribute map
	 */
	private void putAttribute(Map<String, Object> attributeMap) {
		if (attributeMap != null) {
			Iterator<String> iterator = attributeMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = attributeMap.get(key);
				target.put(key, value);
			}
		}
		targetList.add(target);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			org.xml.sax.Attributes attributes) throws SAXException {
		if ("inlineStr".equals(qName) || "v".equals(qName)) {
			vIsOpen = true;
			// Clear contents cache
			value.setLength(0);
		}
		// c => cell
		else if ("c".equals(qName)) {
			// Get the cell reference
			String r = attributes.getValue("r");
			int firstDigit = -1;
			for (int c = 0; c < r.length(); ++c) {
				if (Character.isDigit(r.charAt(c))) {
					firstDigit = c;
					break;
				}
			}
			thisColumn = nameToColumn(r.substring(0, firstDigit));

			// Set up defaults.
			this.nextDataType = XssfDataType.NUMBER;
			this.formatIndex = -1;
			this.formatString = null;
			String cellType = attributes.getValue("t");
			// logger.info(cellType);
			String cellStyleStr = attributes.getValue("s");
			if ("b".equals(cellType)) {
				nextDataType = XssfDataType.BOOL;
			} else if ("e".equals(cellType)) {
				nextDataType = XssfDataType.ERROR;
			} else if ("inlineStr".equals(cellType)) {
				nextDataType = XssfDataType.INLINESTR;
			} else if ("s".equals(cellType)) {
				nextDataType = XssfDataType.SSTINDEX;
			} else if ("str".equals(cellType)) {
				nextDataType = XssfDataType.FORMULA;
			} else if (cellStyleStr != null) {
				// It's a number, but almost certainly one
				// with a special style or format
				int styleIndex = Integer.parseInt(cellStyleStr);
				XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
				this.formatIndex = style.getDataFormat();
				this.formatString = style.getDataFormatString();
				if (this.formatString == null)
					this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
			}
		}
	};

	/**
	 * Captures characters only if a suitable element is open. Originally was just "v"; extended for
	 * inlineStr also.
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (vIsOpen) {
			value.append(ch, start, length);
		}
	}

	/**
	 * Converts an Excel column name like "C" to a zero-based index.
	 *
	 * @param name
	 * @return Index corresponding to the specified name
	 */
	private int nameToColumn(String name) {
		int column = -1;
		for (int i = 0; i < name.length(); ++i) {
			int c = name.charAt(i);
			column = (column + 1) * 26 + c - 'A';
		}
		return column;
	}

	/**
	 * The type of the data value is indicated by an attribute on the cell. The value is usually in
	 * a "v" element within the cell.
	 */
	enum XssfDataType {
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER
	}
}
