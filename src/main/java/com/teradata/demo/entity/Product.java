package com.teradata.demo.entity;

import java.io.Serializable;

/**
 * 商品
 *
 * Created by zhu on 14-1-18.
 */
public class Product implements Serializable {
    private String businessNo;
    private String name;
    private String type;
    private Double sum;
    private String unit;

    /* 统计的结果 */
    private Integer peoples;
    private Double totalSum;
    private Double totalProfit;

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPeoples() {
        return peoples;
    }

    public void setPeoples(Integer peoples) {
        this.peoples = peoples;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }

    public Double getTotalProfit() {
        return totalProfit != null && totalProfit < 0 ? 0 : totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }
}
