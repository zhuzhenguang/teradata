package com.teradata.demo.service;

import com.teradata.demo.entity.Page;
import com.teradata.demo.entity.User;

import java.util.List;

/**
 * 用户服务
 *
 * Created by zhu on 14-1-22.
 */
public interface UserService {
    /**
     * 用户清单
     *
     * @param page
     * @return
     */
    List<User> findUsers(Page page);
}
