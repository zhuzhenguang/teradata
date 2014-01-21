package com.teradata.demo.dao;

import java.util.Map;

/**
 * 从Excel到database的服务
 *
 * Created by zhu on 14-1-20.
 */
public interface ExcelDao {
    /**
     * 从Excel插入
     *
     * @param parameters
     */
    void insert(Map<String, Object>[] parameters);
}
