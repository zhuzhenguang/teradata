package com.teradata.demo.service.impl;

import com.teradata.demo.dao.UserDao;
import com.teradata.demo.entity.Page;
import com.teradata.demo.entity.User;
import com.teradata.demo.service.UserService;

import java.util.List;

/**
 * 用户服务
 *
 * Created by zhu on 14-1-22.
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public List<User> findUsers(Page page) {
        return userDao.findUsers(page.getFrom(), page.getRows());
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
