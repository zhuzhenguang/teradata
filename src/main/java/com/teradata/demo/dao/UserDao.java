package com.teradata.demo.dao;

import com.teradata.demo.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作
 *
 * Created by zhu on 14-1-19.
 */
public interface UserDao extends ExcelDao {
    /**
     * 查找用户
     *
     * @param from 开始index
     * @param rows 取行数
     * @return list
     */
    List<User> findUsers(int from, int rows);
}
