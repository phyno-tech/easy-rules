package ke.co.phyno.learning.easy.rules.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Log
@Component
public class SharedUtils {
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    public String toJson(@NonNull Object data, boolean prettify) {
        return prettify ? this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data) : this.objectMapper.writeValueAsString(data);
    }
}
