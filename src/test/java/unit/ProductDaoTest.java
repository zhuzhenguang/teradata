package unit;

import com.teradata.demo.dao.ProductDao;
import com.teradata.demo.entity.Product;
import com.teradata.demo.entity.StatisticProducts;
import com.teradata.demo.utils.config.Application;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by zhu on 14-1-21.
 */
public class ProductDaoTest {
    private static Logger logger = LoggerFactory.getLogger(ProductDaoTest.class);
    private ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
    ProductDao productDao = context.getBean(ProductDao.class);

    @Test
    public void testFind() {
        List<Product> productList = productDao.findProducts(0, 49);
        assertNotNull(productList);
        assertEquals(49, productList.size());
        for (Product product : productList) {
            logger.info(product.getName());
            logger.info("利润是{}", product.getTotalProfit());
            logger.info("销售总额{}", product.getTotalSum());
        }
    }

    @Test
    public void testStatistic() {
        List<Product> products = productDao.findTopProductsByAddress("上海", 0, 10, "2013-03");
        assertEquals(10, products.size());
        for (Product product : products) {
            logger.info(product.getName() + ": " + product.getTotalProfit());
        }
    }
}
