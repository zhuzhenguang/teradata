package com.teradata.demo.web.vo;

import com.teradata.demo.entity.Page;

/**
 * Created by zhu on 14-1-22.
 */
public class ProductVO {
    private String productId;
    private Page page;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
