package bing.util;

import bing.Constants;
import bing.cache.VisitCache;
import exception.OperationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存加载
 *
 * @author IceWee
 */
public class CacheUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheUtils.class);

    /**
     * 读取历史缓存
     *
     * @return
     */
    public static List<VisitCache> readVisitCache() {
        LOGGER.debug("readVisitCache begin...");
        List<VisitCache> caches = null;
        ObjectInputStream reader = null;
        try {
            String cacheFilePath = getCacheFilePath();
            reader = new ObjectInputStream(new FileInputStream(cacheFilePath));
            Object object = reader.readObject();
            if (object instanceof List) {
                caches = (List<VisitCache>) object;
            }
            LOGGER.debug("readVisitCache end...");
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.CACHE_READ_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(e));
            throw exception;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
            }
        }
        return caches;
    }

    /**
     * 写缓存文件
     *
     * @param caches
     */
    public static void writeVisitCache(List<VisitCache> caches) {
        LOGGER.debug("writeVisitCache begin...");
        ObjectOutputStream writer = null;
        try {
            String cacheFilePath = getCacheFilePath();
            writer = new ObjectOutputStream(new FileOutputStream(cacheFilePath));
            writer.writeObject(caches);
            writer.flush();
            LOGGER.debug("writeVisitCache end...");
        } catch (OperationException e) {
            throw e;
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.CACHE_WRITE_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(e));
            throw exception;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 获得缓存文件路径
     *
     * @return
     */
    private static String getCacheFilePath() {
        LOGGER.debug("getCacheFilePath begin...");
        File file = new File("");
        String getCacheFilePath = file.getAbsolutePath() + File.separator + Constants.CACHE_FILE_PATH;
        LOGGER.debug("getCacheFilePath end...");
        return getCacheFilePath;
    }

}
