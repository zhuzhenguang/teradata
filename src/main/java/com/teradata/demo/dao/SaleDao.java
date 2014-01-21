package com.teradata.demo.dao;

import com.teradata.demo.entity.Sale;

import java.util.List;

/**
 * 销售清单
 * <p/>
 * Created by zhu on 14-1-19.
 */
public interface SaleDao extends ExcelDao {
    /**
     * 根据用户查找购买的商品
     *
     * @param userId
     * @param from
     * @param rows
     * @return
     */
    List<Sale> findGoodsByUser(String userId, int from, int rows);

    /**
     * 根据商品查找购买明细
     *
     * @param productId
     * @param from
     * @param rows
     * @return
     */
    List<Sale> findDetailsByProduct(String productId, int from, int rows);
}
