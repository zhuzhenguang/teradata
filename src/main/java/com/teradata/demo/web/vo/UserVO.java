package com.teradata.demo.web.vo;

import com.teradata.demo.entity.Page;

/**
 * Created by zhu on 14-1-22.
 */
public class UserVO {
    private Page page;
    private String userId;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
