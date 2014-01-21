package com.teradata.demo.web;

import com.teradata.demo.entity.Page;
import com.teradata.demo.entity.Sale;
import com.teradata.demo.entity.User;
import com.teradata.demo.service.SaleService;
import com.teradata.demo.service.UserService;
import com.teradata.demo.web.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户
 *
 * Created by zhu on 14-1-22.
 */
@Controller
public class UserController {
    private UserService userService;
    private SaleService saleService;

    @RequestMapping(value = "/user/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> listUser(@RequestBody Page page) {
        return userService.findUsers(page);
    }

    @RequestMapping(value = "user/sale/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Sale> listSaleByUser(@RequestBody UserVO userVO) {
        return saleService.findGoodsByUser(userVO.getUserId(), userVO.getPage());
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }
}
