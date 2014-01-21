package unit;

import com.teradata.demo.dao.SaleDao;
import com.teradata.demo.dao.UserDao;
import com.teradata.demo.entity.Sale;
import com.teradata.demo.entity.User;
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
public class SaleDaoTest {
    private static Logger logger = LoggerFactory.getLogger(SaleDaoTest.class);
    private ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

    @Test
    public void testFind() {
        SaleDao saleDao = context.getBean(SaleDao.class);
        List<Sale> saleList = saleDao.findGoodsByUser("1555", 0, 60);
        assertNotNull(saleList);
        assertEquals(54, saleList.size());
        for (Sale sale : saleList) {
            logger.info(sale.getSaleDate());
            logger.info(sale.getProduct().getName());
        }
    }

    @Test
    public void testDetail() {
        SaleDao saleDao = context.getBean(SaleDao.class);
        List<Sale> saleList = saleDao.findDetailsByProduct("2000", 0, 49);
        assertNotNull(saleList);
        for (Sale sale : saleList) {
            logger.info(sale.getSaleDate());
            logger.info(sale.getUser().getName());
            logger.info("销售额:{}", sale.getTotalSum());
        }
    }
}
