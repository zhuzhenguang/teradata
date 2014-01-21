package com.teradata.demo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售
 *
 * Created by zhu on 14-1-18.
 */
public class Sale implements Serializable {
    private String userBusinessNo;
    private String productBusinessNo;
    private String saleDate;
    private Double sum;
    private Double count;

    private Product product;
    private User user;
    private Double totalSum;

    public String getUserBusinessNo() {
        return userBusinessNo;
    }

    public void setUserBusinessNo(String userBusinessNo) {
        this.userBusinessNo = userBusinessNo;
    }

    public String getProductBusinessNo() {
        return productBusinessNo;
    }

    public void setProductBusinessNo(String productBusinessNo) {
        this.productBusinessNo = productBusinessNo;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }
}
