package com.teradata.demo.web;

import com.teradata.demo.entity.StatisticProducts;
import com.teradata.demo.service.ProductService;
import com.teradata.demo.web.vo.StatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统计
 *
 * Created by zhu on 14-1-22.
 */
@Controller
public class StatisticController {
    private ProductService productService;

    @RequestMapping(value = "/statistic", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody StatisticProducts statisticProducts(@RequestBody StatisticVO statisticVO) {
        return productService.findTopProductsByAddressMonth(statisticVO.getAddress(), statisticVO.getPage());
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
