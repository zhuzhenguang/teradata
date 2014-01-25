package com.teradata.demo.service;

import com.teradata.demo.entity.Address;

import java.util.List;

/**
 * 地址
 *
 * Created by zhu on 14-1-25.
 */
public interface AddressService {
    /**
     * 查询地区
     *
     * @param queryString
     * @return
     */
    List<Address> search(String queryString);
}
