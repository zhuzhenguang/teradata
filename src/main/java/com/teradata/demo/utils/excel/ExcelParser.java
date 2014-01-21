package com.teradata.demo.utils.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class ExcelParser {
	public static int MAX_BATCH = 2000;
	private static Logger logger = LoggerFactory.getLogger(ExcelParser.class);

	private OPCPackage xlsxPackage;
	private Map<String, String> columnMap;
	private ExcelBigService excelService;
	private Map<String, Object> attributeMap;

	public ExcelParser(OPCPackage pkg, Map<String, String> columnMap, ExcelBigService excelService,
			Map<String, Object> attributeMap) {
		this.xlsxPackage = pkg;
		this.columnMap = columnMap;
		this.excelService = excelService;
		this.attributeMap = attributeMap;
	}

	/**
	 * Parses and shows the content of one sheet using the specified styles and shared-strings
	 * tables.
	 *
	 * @param styles
	 * @param strings
	 * @param sheetInputStream
	 */
	public void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
			InputStream sheetInputStream) throws IOException, ParserConfigurationException,
			SAXException {

		InputSource sheetSource = new InputSource(sheetInputStream);
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxFactory.newSAXParser();
		XMLReader sheetParser = saxParser.getXMLReader();
		ContentHandler handler = new ExcelEventHandler(styles, strings, columnMap, MAX_BATCH,
				excelService, attributeMap);
		sheetParser.setContentHandler(handler);
		sheetParser.parse(sheetSource);
	}

	/**
	 * Initiates the processing of the XLS workbook file to CSV.
	 *
	 * @throws java.io.IOException
	 * @throws org.apache.poi.openxml4j.exceptions.OpenXML4JException
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws org.xml.sax.SAXException
	 */
	public void process() throws IOException, OpenXML4JException, ParserConfigurationException,
			SAXException {

		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		int index = 0;
		while (iter.hasNext()) {
			InputStream stream = iter.next();
			String sheetName = iter.getSheetName();
			logger.info("");
			logger.info("{} [index={}]:", sheetName, index);
			processSheet(styles, strings, stream);
			stream.close();
			++index;
		}
	}

}
