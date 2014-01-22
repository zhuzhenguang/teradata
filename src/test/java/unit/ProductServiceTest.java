package unit;

import com.teradata.demo.entity.Page;
import com.teradata.demo.entity.Product;
import com.teradata.demo.entity.StatisticProducts;
import com.teradata.demo.service.ProductService;
import com.teradata.demo.utils.config.Application;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

/**
 * User: zhenguang.zhu
 * Date: 14-1-22
 * Time: 下午12:30
 */
public class ProductServiceTest {
    private static Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    private ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
    private ProductService productService = context.getBean(ProductService.class);

    @Test
    public void testStatistic() {
        StatisticProducts statisticProducts = productService.findTopProductsByAddressMonth("上海", new Page(0, 10));
        assertEquals("2013-03", statisticProducts.getMaxMonth());
        assertEquals("2013-02", statisticProducts.getPreviousMonth());

        for (Product product : statisticProducts.getMaxTopProductList()) {
            logger.info(product.getBusinessNo() + ":" + product.getTotalProfit());
        }

        logger.info("========================================");

        for (Product product: statisticProducts.getPreviousProductList()) {
            logger.info(product.getBusinessNo() + ":" + product.getTotalProfit());
        }

    }
}
