package unit;

import com.teradata.demo.dao.AddressDao;
import com.teradata.demo.entity.Address;
import com.teradata.demo.utils.config.Application;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Created by zhu on 14-1-25.
 */
public class AddressDaoTest {
    private static Logger logger = LoggerFactory.getLogger(AddressDaoTest.class);
    private ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
    private AddressDao addressDao = context.getBean(AddressDao.class);

    @Test
    public void testSearchAddress() {
        List<Address> addressList = addressDao.list("北");
        for (Address address : addressList) {
            logger.info(address.getName());
        }
    }
}
