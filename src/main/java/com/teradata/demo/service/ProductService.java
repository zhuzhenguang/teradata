package com.teradata.demo.service;

import com.teradata.demo.entity.Page;
import com.teradata.demo.entity.Product;
import com.teradata.demo.entity.StatisticProducts;

import java.util.List;

/**
 * 商品服务
 *
 * Created by zhu on 14-1-22.
 */
public interface ProductService {
    /**
     * 商品清单
     *
     * @param page
     * @return
     */
    List<Product> findProducts(Page page);

    /**
     * 根据月别和地区统计商品
     *
     * @param address
     * @param page
     * @return
     */
    StatisticProducts findTopProductsByAddressMonth(String address, Page page);
}
