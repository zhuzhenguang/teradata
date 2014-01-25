package com.teradata.demo.dao;

import com.teradata.demo.entity.Address;

import java.util.List;

/**
 * 地址操作
 *
 * Created by zhu on 14-1-25.
 */
public interface AddressDao extends ExcelDao {
    /**
     * 模糊查询地址
     *
     * @param queryString
     * @return
     */
    List<Address> list(String queryString);
}
