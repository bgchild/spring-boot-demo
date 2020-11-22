import com.lk.Application;
import com.lk.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ann00
 * @date 2020/8/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class batchTest {
    @Autowired
    UserService userService;
    @Test
    public void batchTests(){
        userService.batchInset();
    }
}
