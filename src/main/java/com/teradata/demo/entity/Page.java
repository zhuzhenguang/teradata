package com.teradata.demo.entity;

/**
 * Created by zhu on 14-1-22.
 */
public class Page {
    private Integer from;
    private Integer rows;

    public Page() {
    }

    public Page(Integer from, Integer rows) {
        this.from = from;
        this.rows = rows;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
