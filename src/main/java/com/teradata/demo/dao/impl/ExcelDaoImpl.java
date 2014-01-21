package com.teradata.demo.dao.impl;

import com.teradata.demo.dao.ExcelDao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import java.util.Map;

/**
 * 操作Excel数据
 *
 * Created by zhu on 14-1-20.
 */
public abstract class ExcelDaoImpl extends NamedParameterJdbcDaoSupport implements ExcelDao {
    @Override
    public void insert(Map<String, Object>[] parameters) {
        getNamedParameterJdbcTemplate().batchUpdate(getSQL(), parameters);
    }

    protected abstract String getSQL();
}
