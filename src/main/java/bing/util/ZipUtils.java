package bing.util;

import exception.OperationException;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 压缩工具类
 *
 * @author IceWee
 */
public class ZipUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class);
    private static final int CACHE_SIZE = 1024;
    private static final int INVALID_SIZE = -1;

    /**
     * 解压
     *
     * @param bytes
     * @return
     */
    public static byte[] unzip(byte[] bytes) {
        LOGGER.debug("unzip begin...");
        ByteArrayOutputStream output = null;
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(bytes, 0, bytes.length);
            output = new ByteArrayOutputStream();
            byte[] result = new byte[CACHE_SIZE];
            int size = INVALID_SIZE;
            while (size != 0) {
                size = inflater.inflate(result, 0, result.length);
                output.write(result, 0, size);
            }
            inflater.end();
            byte[] unzipBytes = output.toByteArray();
            LOGGER.debug("unzip end...");
            return unzipBytes;
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.ZIP_UNCOMPRESS_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(exception));
            throw exception;
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 压缩
     *
     * @param bytes
     * @return
     */
    public static byte[] zip(byte[] bytes) {
        LOGGER.debug("zip begin...");
        ByteArrayOutputStream output = null;
        try {
            Deflater deflater = new Deflater();
            deflater.setInput(bytes);
            deflater.finish();
            output = new ByteArrayOutputStream();
            byte[] cache = new byte[CACHE_SIZE];
            while (!deflater.finished()) {
                int byteCount = deflater.deflate(cache);
                output.write(cache, 0, byteCount);
            }
            deflater.end();
            byte[] zipBytes = output.toByteArray();
            LOGGER.debug("zip end...");
            return zipBytes;
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.ZIP_COMPRESS_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(exception));
            throw exception;
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
            }
        }
    }

}
