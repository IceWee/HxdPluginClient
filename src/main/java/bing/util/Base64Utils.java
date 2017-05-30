package bing.util;

import exception.OperationException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BASE64工具类
 *
 * @author IceWee
 */
public class Base64Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Base64Utils.class);

    /**
     * 编码
     *
     * @param bytes
     * @return
     */
    public static String encode(byte[] bytes) {
        LOGGER.debug("encode begin...");
        try {
            String base64 = Base64.encodeBase64URLSafeString(bytes);
            LOGGER.debug("encode end...");
            return base64;
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.BASE64_ENCODE_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(exception));
            throw exception;
        }
    }

}
