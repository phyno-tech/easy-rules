package ke.co.phyno.learning.easy.rules.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @SneakyThrows(value = JsonProcessingException.class)
    public String toJson(@NonNull Object data, boolean prettify) {
        return prettify ? this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data) : this.objectMapper.writeValueAsString(data);
    }

    @SneakyThrows(value = JsonProcessingException.class)
    public <T> T fromJson(String data, @NonNull Class<T> type) {
        if (this.isNullOrEmptyOrBlank(data)) {
            return null;
        }
        return this.objectMapper.readValue(data.trim(), type);
    }

    public boolean isNullOrEmptyOrBlank(String s) {
        return s == null || s.trim().isEmpty() || s.trim().isBlank();
    }
}
