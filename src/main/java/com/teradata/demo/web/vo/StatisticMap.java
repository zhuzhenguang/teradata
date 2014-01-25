package com.teradata.demo.web.vo;

import com.teradata.demo.entity.Product;
import com.teradata.demo.entity.StatisticProducts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: zhenguang.zhu
 * Date: 14-1-22
 * Time: 下午2:24
 */
public class StatisticMap {
    private Map<String, Product> topProducts = new LinkedHashMap<String, Product>(10);
    private Map<String, Product> preProducts = new LinkedHashMap<String, Product>(10);
    private List<String> productNames = new ArrayList<String>(10);
    private String topMonth;
    private String preMonth;

    public StatisticMap() {
    }

    public StatisticMap(StatisticProducts statisticProducts) {
        if (statisticProducts == null) {
            return;
        }
        setTopMonth(statisticProducts.getMaxMonth());
        setPreMonth(statisticProducts.getPreviousMonth());
        for (Product product : statisticProducts.getMaxTopProductList()) {
            topProducts.put(product.getBusinessNo(), product);
            productNames.add(product.getBusinessNo());
        }
        for (Product product : statisticProducts.getPreviousProductList()) {
            preProducts.put(product.getBusinessNo(), product);
        }
    }

    public Map<String, Product> getTopProducts() {
        return topProducts;
    }

    public void setTopProducts(Map<String, Product> topProducts) {
        this.topProducts = topProducts;
    }

    public Map<String, Product> getPreProducts() {
        return preProducts;
    }

    public void setPreProducts(Map<String, Product> preProducts) {
        this.preProducts = preProducts;
    }

    public String getTopMonth() {
        return topMonth;
    }

    public void setTopMonth(String topMonth) {
        this.topMonth = topMonth;
    }

    public String getPreMonth() {
        return preMonth;
    }

    public void setPreMonth(String preMonth) {
        this.preMonth = preMonth;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
