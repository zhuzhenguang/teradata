package com.teradata.demo.web;

import com.teradata.demo.entity.StatisticProducts;
import com.teradata.demo.service.ProductService;
import com.teradata.demo.web.vo.StatisticMap;
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

    @RequestMapping("/statistic")
    public String init() {
        return "statistic";
    }

    @RequestMapping(value = "/statistic/data", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody StatisticMap statisticProducts(@RequestBody StatisticVO statisticVO) {
        StatisticProducts statisticProducts = productService.findTopProductsByAddressMonth(
                statisticVO.getAddress(), statisticVO.getPage());
        return new StatisticMap(statisticProducts);
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
