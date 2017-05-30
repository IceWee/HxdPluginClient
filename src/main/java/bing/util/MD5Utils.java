package bing.util;

import bing.Constants;
import exception.OperationException;
import java.security.MessageDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5工具类
 *
 * @author IceWee
 */
public class MD5Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Utils.class);

    /**
     * 获得MD5字节数组
     *
     * @param string
     * @return
     */
    public static byte[] getMD5ByteArray(String string) {
        LOGGER.debug("getMD5ByteArray begin...");
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(string.getBytes(Constants.ENCODING_UTF8));
            byte[] bytes = messageDigest.digest();
            LOGGER.debug("getMD5ByteArray end...");
            return bytes;
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.MD5_GENERATE_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(exception));
            throw exception;
        }

    }

}
