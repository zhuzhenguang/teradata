package com.teradata.demo.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 地址
 * <p/>
 * Created by zhu on 14-1-25.
 */
public class Address {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getName()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Address && new EqualsBuilder().append(this.getName(), ((Address) obj).getName()).isEquals();
    }
}
