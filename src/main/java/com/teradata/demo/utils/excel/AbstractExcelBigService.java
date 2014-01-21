package com.teradata.demo.utils.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

/**
 * The Class AbstractExcelBigService. 大数据Excle的抽象
 */
public abstract class AbstractExcelBigService implements ExcelBigService {
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void excelImport(InputStream inputstream, Map<String, String> COLUMN_MAP,
			Map<String, Object> attributeMap) throws Exception {
		/* 创建临时文件 */
		File tempFile = createTempFile(inputstream);
		OPCPackage opcPackage = OPCPackage.open(tempFile.getAbsolutePath(), PackageAccess.READ);
		/* 解析 */
		ExcelParser excelParser = new ExcelParser(opcPackage, COLUMN_MAP, this, attributeMap);
		excelParser.process();
		/* 关闭 */
		opcPackage.close();
		tempFile.delete();
		inputstream.close();
	}

	/**
	 * Creates the temp file. 创建临时文件
	 *
	 * @param inputstream the inputstream
	 * @return the file
	 * @throws java.io.IOException Signals that an I/O exception has occurred.
	 */
	private File createTempFile(InputStream inputstream) throws IOException {
		File tempFile = File.createTempFile("temp", ".xlsx");
		byte[] byteArray = ByteStreams.toByteArray(inputstream);
		Files.write(byteArray, tempFile);
		return tempFile;
	}

	@Override
	public abstract Integer batchInsert(List<Map<String, Object>> targetList, int count);
}
