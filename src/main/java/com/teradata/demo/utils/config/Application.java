package com.teradata.demo.utils.config;

import com.teradata.demo.dao.ProductDao;
import com.teradata.demo.dao.SaleDao;
import com.teradata.demo.dao.UserDao;
import com.teradata.demo.dao.impl.ProductDaoImpl;
import com.teradata.demo.dao.impl.SaleDaoImpl;
import com.teradata.demo.dao.impl.UserDaoImpl;
import com.teradata.demo.entity.User;
import com.teradata.demo.service.*;
import com.teradata.demo.service.impl.ExcelServiceImpl;
import com.teradata.demo.service.impl.ProductServiceImpl;
import com.teradata.demo.service.impl.SaleServiceImpl;
import com.teradata.demo.service.impl.UserServiceImpl;
import com.teradata.demo.web.ExcelController;
import com.teradata.demo.web.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 配置
 *
 * Created by zhu on 14-1-19.
 */
@Configuration
@ComponentScan(basePackages = {"com.teradata.demo"})
@EnableTransactionManagement
public class Application {
    @Bean
    DataSource dataSource() {
        return new SimpleDriverDataSource() {{
            setDriverClass(org.h2.Driver.class);
            setUsername("sa");
            setUrl("jdbc:h2:~/test");
            setPassword("");
        }};
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("runscript from 'classpath:/schema.sql'");
        //jdbcTemplate.execute("runscript from 'classpath:/data.sql'");
        return jdbcTemplate;
    }

    @Bean
    public DemoService mockSay() {
        return new DemoService() {
            @Override
            public String say() {
                return "Hello";
            }
        };
    }

    @Bean
    public UserDao userDao() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate(dataSource()));
        return userDao;
    }

    @Bean
    public ProductDao productDao() {
        ProductDaoImpl productDao = new ProductDaoImpl();
        productDao.setJdbcTemplate(jdbcTemplate(dataSource()));
        return productDao;
    }

    @Bean
    public SaleDao saleDao() {
        SaleDaoImpl saleDao = new SaleDaoImpl();
        saleDao.setJdbcTemplate(jdbcTemplate(dataSource()));
        return saleDao;
    }

    @Bean
    public ExcelService excelService() {
        ExcelServiceImpl excelService = new ExcelServiceImpl();
        excelService.setProductDao(productDao());
        excelService.setSaleDao(saleDao());
        excelService.setUserDao(userDao());
        return excelService;
    }

    @Bean
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao());
        return userService;
    }

    @Bean
    public ProductService productService() {
        ProductServiceImpl productService = new ProductServiceImpl();
        productService.setProductDao(productDao());
        return productService;
    }

    @Bean
    public SaleService saleService() {
        SaleServiceImpl saleService = new SaleServiceImpl();
        saleService.setSaleDao(saleDao());
        return saleService;
    }

    /*@Bean
    @Scope("prototype")
    public ExcelController excelController() {
        ExcelController excelController = new ExcelController();
        excelController.setExcelService(excelService());
        return excelController;
    }

    @Bean
    @Scope("prototype")
    public UserController userController() {
        UserController userController = new UserController();
        userController.setUserService(userService());
        return userController;
    }*/

}
