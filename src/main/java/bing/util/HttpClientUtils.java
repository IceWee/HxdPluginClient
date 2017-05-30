package bing.util;

import com.alibaba.fastjson.JSONObject;
import exception.OperationException;
import java.io.InputStream;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP请求工具类
 *
 * @author IceWee
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
    private static CloseableHttpClient HTTP_CLIENT;
    private static String PARAM_NAME = "info";

    /**
     * 创建HTTP客户端
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        LOGGER.debug("call getHttpClient");
        if (HTTP_CLIENT == null) {
            synchronized (HttpClientUtils.class) {
                if (HTTP_CLIENT == null) {
                    HTTP_CLIENT = HttpClientBuilder.create().build();
                }
            }
        }
        return HTTP_CLIENT;
    }

    /**
     * 发送HTTP请求
     *
     * @param url
     * @param jsonString
     * @return
     */
    public static String httpGet(String url, String jsonString) {
        LOGGER.debug("httpGet begin...");
        try {
            String finalUrl = url + "&" + PARAM_NAME + "=" + AESUtils.encryptWithBase64(jsonString);
            LOGGER.info("请求地址：{}", finalUrl);
            HttpGet httpGet = new HttpGet(finalUrl);
            HttpResponse httpResponse = getHttpClient().execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            System.out.println(Arrays.toString(bytes));
            String string = parseResponse(bytes);
            LOGGER.debug("httpGet end...");
            return string;
        } catch (OperationException e) {
            throw e;
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.HTTP_REQUEST_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(exception));
            throw exception;
        }
    }

    /**
     * 返回byte数组解析 2位type+4位errorCode+4位length1+?位content+4位length2+16位密钥
     *
     * @param bytes
     * @return
     */
    private static String parseResponse(byte[] bytes) {
        LOGGER.debug("parseResponse begin...");
        try {
            int length = bytes.length;
            int type = byteArrayToInt(bytes, 0, 2);
            int errorCode = byteArrayToInt(bytes, 2, 6);
            int length1 = byteArrayToInt(bytes, 6, 10);
            int contentLength = length - 20 - 10;
            byte[] contentBytes = new byte[contentLength];
            System.arraycopy(bytes, 10, contentBytes, 0, contentLength);
            String content = AESUtils.decryptBytesToString(contentBytes);
            JSONObject jsonObject = JSONObject.parseObject(content);
            int length2 = byteArrayToInt(bytes, length - 20, length - 16);
            JSONObject json = new JSONObject();
            json.put("type", type);
            json.put("errorCode", errorCode);
            json.put("length1", length1);
            json.put("content", jsonObject);
            json.put("length2", length2);
            String string = json.toJSONString();
            LOGGER.debug("parseResponse end...");
            return string;
        } catch (OperationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字节数组转换为int
     *
     * @param bytes
     * @param offset
     * @param length
     * @return
     */
    public static int byteArrayToInt(byte[] bytes, int offset, int length) {
        LOGGER.debug("byteArrayToInt begin...");
        int value = 0;
        for (int i = offset; i < length; i++) { //由高位到低位
            int shift = (length - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;//往高位游
        }
        LOGGER.debug("byteArrayToInt end...");
        return value;
    }

    /**
     * 接口地址校验
     *
     * @param url
     * @return
     */
    public static boolean isUrlValid(String url) {
        LOGGER.debug("isUrlValid begin...");
        boolean valid = StringUtils.isNotBlank(url) && StringUtils.contains(url, "http") && StringUtils.contains(url, "plugin.servlet?type=");
        LOGGER.debug("isUrlValid end...");
        return valid;
    }

}
