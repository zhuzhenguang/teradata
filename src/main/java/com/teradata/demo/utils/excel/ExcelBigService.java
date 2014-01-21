package com.teradata.demo.utils.excel;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * The Interface ExcelBigService. 支持大数据量的Excel上传的服务
 */
public interface ExcelBigService {

	/**
	 * Excel import. 导入
	 *
	 * @param stream the stream
	 * @param columnMap the column map
	 * @param attributeMap the attribute map
	 * @throws Exception the exception
	 */
	void excelImport(InputStream stream, Map<String, String> columnMap,
                     Map<String, Object> attributeMap) throws Exception;

	/**
	 * Batch insert. 批量执行业务
	 *
	 * @param targetList the target list
	 * @param count the count
	 * @return the integer
	 */
	Integer batchInsert(List<Map<String, Object>> targetList, int count);
}
