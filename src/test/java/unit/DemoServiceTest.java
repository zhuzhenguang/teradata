package unit;

import com.teradata.demo.service.DemoService;
import com.teradata.demo.utils.config.Application;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhu on 14-1-19.
 */
public class DemoServiceTest {
    private ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

    @Test
    public void test() {
        DemoService demoService = context.getBean(DemoService.class);
        assertEquals("Hello", demoService.say());
    }
}
