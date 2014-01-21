package com.teradata.demo.service;

import java.io.InputStream;

/**
 * Excel服务
 *
 * Created by zhu on 14-1-20.
 */
public interface ExcelService {
    /**
     * 解析
     *
     * @param excelStream excel文件流
     */
    void parse(InputStream excelStream);
}
