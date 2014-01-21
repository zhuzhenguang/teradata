package com.teradata.demo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * Created by zhu on 14-1-18.
 */
public class User implements Serializable {
    private String businessNo;
    private String name;
    private String address;
    private String birthday;
    private String sex;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
