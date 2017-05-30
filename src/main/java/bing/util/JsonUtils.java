package bing.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import exception.OperationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON工具类
 *
 * @author IceWee
 */
public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * JSON格式化
     *
     * @param json
     * @return
     */
    public static String formatJson(String json) {
        LOGGER.debug("formatJson begin...");
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(json);
            String formatJson = gson.toJson(jsonElement);
            LOGGER.debug("formatJson end...");
            return formatJson;
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.JSON_FORMAT_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(e));
            throw exception;
        }
    }

    /**
     * JSON校验
     *
     * @param json
     * @return
     */
    public static boolean valid(String json) {
        LOGGER.debug("valid begin...");
        boolean valid = true;
        try {
            if (StringUtils.isNotBlank(StringUtils.trim(json))) {
                new JsonParser().parse(json);
            }
        } catch (Exception e) {
            LOGGER.debug(ExceptionUtils.createExceptionString(e));
            valid = false;
        }
        LOGGER.debug("valid end...");
        return valid;
    }

}
