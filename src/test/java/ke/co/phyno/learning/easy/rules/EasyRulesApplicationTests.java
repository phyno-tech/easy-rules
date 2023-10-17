package ke.co.phyno.learning.easy.rules;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Level;

@Log
@SpringBootTest
public class EasyRulesApplicationTests {
    @Test
    public void contextLoads() {
        log.log(Level.INFO, "Testing application");
    }
}
