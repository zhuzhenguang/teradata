package com.teradata.demo.web;

import com.teradata.demo.entity.Page;
import com.teradata.demo.entity.Product;
import com.teradata.demo.entity.Sale;
import com.teradata.demo.service.ProductService;
import com.teradata.demo.service.SaleService;
import com.teradata.demo.web.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品
 *
 * Created by zhu on 14-1-22.
 */
@Controller
public class ProductController {
    private ProductService productService;
    private SaleService saleService;

    @RequestMapping("/product")
    public String init() {
        return "product";
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Product> listProducts(@RequestBody Page page) {
        return productService.findProducts(page);
    }

    @RequestMapping(value = "/product/sale/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Sale> listSaleByProduct(@RequestBody ProductVO productVO) {
        return saleService.findDetailsByProduct(productVO.getProductId(), productVO.getPage());
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }
}
