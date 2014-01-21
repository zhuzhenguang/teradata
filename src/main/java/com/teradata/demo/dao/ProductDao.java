package com.teradata.demo.dao;

import com.teradata.demo.entity.Product;
import org.joda.time.DateTime;

import java.util.List;

/**
 * 商品操作
 *
 * Created by zhu on 14-1-19.
 */
public interface ProductDao extends ExcelDao {
    /**
     * 列出所有商品
     *
     * @param from from
     * @param rows rows
     * @return list
     */
    List<Product> findProducts(int from, int rows);

    /**
     * 根据城市查询商品
     *
     * @param address
     * @param from
     * @param rows
     * @return
     */
    List<Product> findTopProductsByAddress(String address, int from, int rows, String month);

    /**
     * 获得最近的一个月
     *
     * @param address
     * @return
     */
    DateTime getMaxDay(String address);
}
