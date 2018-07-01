package priv.autumn4j.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * pojo2json
     *
     * @param model
     * @return
     */
    public static String toJson(Object model) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(model);
            return json;
        } catch (JsonProcessingException e) {
            LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException("pojo转json失败");
        }
    }

    /**
     * json2pojo
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toPOJO(String json, Class<T> clazz) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, clazz);
            return pojo;
        } catch (IOException e) {
            LOGGER.error("convert JSON to POJO failure", e);
            throw new RuntimeException("json转pojo失败");
        }

    }
}
