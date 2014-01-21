package com.teradata.demo.web.vo;

import com.teradata.demo.entity.Page;

/**
 * Created by zhu on 14-1-22.
 */
public class StatisticVO {
    private String address;
    private Page page;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
