package com.teradata.demo.service;

import com.teradata.demo.entity.Page;
import com.teradata.demo.entity.Sale;

import java.util.List;

/**
 * 销售服务
 *
 * Created by zhu on 14-1-22.
 */
public interface SaleService {
    /**
     * 查找某个用户的购买清单
     * @param userId
     * @param page
     * @return
     */
    List<Sale> findGoodsByUser(String userId, Page page);

    /**
     * 查找某个商品的销售清单
     *
     * @param productId
     * @param page
     * @return
     */
    List<Sale> findDetailsByProduct(String productId, Page page);
}
