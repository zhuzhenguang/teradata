package com.teradata.demo.service.impl;

import com.teradata.demo.dao.SaleDao;
import com.teradata.demo.entity.Page;
import com.teradata.demo.entity.Sale;
import com.teradata.demo.service.SaleService;

import java.util.List;

/**
 * 销售服务
 *
 * Created by zhu on 14-1-22.
 */
public class SaleServiceImpl implements SaleService {
    private SaleDao saleDao;

    @Override
    public List<Sale> findGoodsByUser(String userId, Page page) {
        return saleDao.findGoodsByUser(userId, page.getFrom(), page.getRows());
    }

    @Override
    public List<Sale> findDetailsByProduct(String productId, Page page) {
        return saleDao.findDetailsByProduct(productId, page.getFrom(), page.getRows());
    }

    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }
}
