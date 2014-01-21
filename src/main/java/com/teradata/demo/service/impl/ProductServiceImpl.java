package com.teradata.demo.service.impl;

import com.teradata.demo.dao.ProductDao;
import com.teradata.demo.entity.Page;
import com.teradata.demo.entity.Product;
import com.teradata.demo.entity.StatisticProducts;
import com.teradata.demo.service.ProductService;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品服务
 * <p/>
 * Created by zhu on 14-1-22.
 */
public class ProductServiceImpl implements ProductService {
    private static final String DATE_STYLE = "yyyy-MM";
    private ProductDao productDao;

    @Override
    public List<Product> findProducts(Page page) {
        return productDao.findProducts(page.getFrom(), page.getRows());
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticProducts findTopProductsByAddressMonth(String address, Page page) {
        DateTime maxDateTime = productDao.getMaxDay(address);
        String previousMonthDay = maxDateTime.minus(Period.months(1)).toString(DATE_STYLE);
        String maxMonthDay = maxDateTime.toString(DATE_STYLE);

        StatisticProducts statisticProduct = new StatisticProducts();
        statisticProduct.setMaxMonth(maxMonthDay);
        statisticProduct.setPreviousMonth(previousMonthDay);
        statisticProduct.setMaxTopProductList(productDao.findTopProductsByAddress(
                address, page.getFrom(), page.getRows(), maxMonthDay));
        statisticProduct.setPreviousProductList(productDao.findTopProductsByAddress(
                address, page.getFrom(), page.getRows(), previousMonthDay));

        return statisticProduct;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
