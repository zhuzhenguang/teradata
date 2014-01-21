package unit;

import com.teradata.demo.dao.UserDao;
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
public class UserDaoTest {
    private static Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
    private ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

    @Test
    public void testFind() {
        UserDao userDao = context.getBean(UserDao.class);
        List<User> userList = userDao.findUsers(0, 20);
        assertNotNull(userList);
        assertEquals(20, userList.size());
        for (User user : userList) {
            logger.info(user.getName());
        }
    }
}
