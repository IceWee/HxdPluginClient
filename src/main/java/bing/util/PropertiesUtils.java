package bing.util;

import bing.Constants;
import exception.OperationException;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源文件工具类
 *
 * @author IceWee
 */
public class PropertiesUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
    private static final Properties PROPERTIES = new Properties();

    static {
        loadPropertiesFile();
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * 更新配置文件
     *
     * @param key
     * @param value
     */
    public static void updateProperty(String key, String value) {
        LOGGER.debug("updateProperty begin...");
        FileOutputStream out = null;
        try {
            String configFilePath = getConfigFilePath();
            out = new FileOutputStream(configFilePath);
            PROPERTIES.setProperty(key, value);
            PROPERTIES.store(out, "updated");
            LOGGER.debug("updateProperty end...");
        } catch (FileNotFoundException e) {
            String error = ExceptionUtils.createExceptionString(e);
            LOGGER.debug("配置文件丢失\n{}", error);
        } catch (IOException e) {
            String error = ExceptionUtils.createExceptionString(e);
            LOGGER.debug("更新配置文件失败\n{}", error);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 加载默认配置文件
     */
    private static void loadPropertiesFile() {
        LOGGER.debug("loadPropertiesFile begin...");
        InputStream in = null;
        try {
            String configFilePath = getConfigFilePath();
            File configFile = new File(configFilePath);
            if (!configFile.exists()) {
                createConfigFile();
            }
            in = new BufferedInputStream(new FileInputStream(configFilePath));
            PROPERTIES.load(in);
            LOGGER.debug("loadPropertiesFile end...");
        } catch (FileNotFoundException e) {
            String error = ExceptionUtils.createExceptionString(e);
            LOGGER.debug("配置文件丢失\n{}", error);
        } catch (IOException e) {
            String error = ExceptionUtils.createExceptionString(e);
            LOGGER.debug("读取配置文件失败\n{}", error);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 生成配置文件
     */
    private static void createConfigFile() {
        LOGGER.debug("createConfigFile begin...");
        BufferedWriter writer = null;
        try {
            String configFilePath = getConfigFilePath();
            writer = new BufferedWriter(new FileWriter(configFilePath));
            writer.write(Constants.CONFIG_KEY_THEME);
            writer.flush();
            LOGGER.debug("createConfigFile end...");
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
     * 获得配置文件路径
     *
     * @return
     */
    private static String getConfigFilePath() {
        LOGGER.debug("getConfigFilePath begin...");
        File file = new File("");
        String configFilePath = file.getAbsolutePath() + File.separator + Constants.CONFIG_FILE_PATH;
        LOGGER.debug("getConfigFilePath end...");
        return configFilePath;
    }

}
