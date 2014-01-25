package com.teradata.demo.service.impl;

import com.teradata.demo.dao.AddressDao;
import com.teradata.demo.entity.Address;
import com.teradata.demo.service.AddressService;

import java.util.List;

/**
 * 地区
 *
 * Created by zhu on 14-1-25.
 */
public class AddressServiceImpl implements AddressService {
    private AddressDao addressDao;

    @Override
    public List<Address> search(String queryString) {
        return addressDao.list(queryString);
    }

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }
}
