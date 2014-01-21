package com.teradata.demo.entity;

import java.util.List;

/**
 * 商品统计结果
 *
 * Created by zhu on 14-1-22.
 */
public class StatisticProducts {
    private String maxMonth;
    private String previousMonth;
    private List<Product> maxTopProductList;
    private List<Product> previousProductList;

    public String getMaxMonth() {
        return maxMonth;
    }

    public void setMaxMonth(String maxMonth) {
        this.maxMonth = maxMonth;
    }

    public String getPreviousMonth() {
        return previousMonth;
    }

    public void setPreviousMonth(String previousMonth) {
        this.previousMonth = previousMonth;
    }

    public List<Product> getMaxTopProductList() {
        return maxTopProductList;
    }

    public void setMaxTopProductList(List<Product> maxTopProductList) {
        this.maxTopProductList = maxTopProductList;
    }

    public List<Product> getPreviousProductList() {
        return previousProductList;
    }

    public void setPreviousProductList(List<Product> previousProductList) {
        this.previousProductList = previousProductList;
    }
}
