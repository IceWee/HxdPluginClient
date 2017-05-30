package bing.util;

import bing.Constants;
import exception.OperationException;
import java.util.List;
import java.security.Key;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES工具类
 *
 * @author IceWee
 */
public class AESUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);
    public final static String KEY = "8F, Block E, Dazhongsi Zhongkun Plaza, No. A18 West Beisanhuan Road, Haidian District, Beijing";
    public final static String SALT = "7238799724734f41992b3890d575bb1d";
    public static final String KEY_ALGORITHM = "AES";
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 加密字符串
     *
     * @param string
     * @return
     */
    public static String encryptWithBase64(String string) {
        LOGGER.debug("encryptWithBase64 begin...");
        try {
            byte[] bytes = ZipUtils.zip(string.getBytes(Constants.ENCODING_UTF8));
            byte[] encryptBytes = encrypt(bytes);
            String base64 = Base64Utils.encode(encryptBytes);
            LOGGER.debug("encryptWithBase64 end...");
            return base64;
        } catch (OperationException e) {
            throw e;
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.AES_ENCRYPT_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(exception));
            throw exception;
        }
    }

    /**
     * 解密字符串
     *
     * @param bytes
     * @return
     */
    public static String decryptBytesToString(byte[] bytes) {
        LOGGER.debug("decryptBytesToString begin...");
        try {
            byte[] decryptBytes = decrypt(bytes);
            String string = new String(ZipUtils.unzip(decryptBytes), Constants.ENCODING_UTF8);
            LOGGER.debug("decryptBytesToString end...");
            return string;
        } catch (OperationException e) {
            throw e;
        } catch (Exception e) {
            RuntimeException exception = new OperationException(OperationException.AES_DECRYPT_FAILED, e);
            LOGGER.debug(ExceptionUtils.createExceptionString(exception));
            throw exception;
        }
    }

    /**
     * 加密
     *
     * @param bytes
     * @return
     */
    private static byte[] encrypt(byte[] bytes) {
        LOGGER.debug("encrypt begin...");
        try {
            Key key = genSecretKey();
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptBytes = cipher.doFinal(bytes);
            LOGGER.debug("encrypt end...");
            return encryptBytes;
        } catch (OperationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.debug(ExceptionUtils.createExceptionString(e));
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param bytes
     * @return
     */
    private static byte[] decrypt(byte[] bytes) {
        LOGGER.debug("decrypt begin...");
        try {
            Key key = genSecretKey();
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptBytes = cipher.doFinal(bytes);
            LOGGER.debug("decrypt end...");
            return decryptBytes;
        } catch (OperationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.debug(ExceptionUtils.createExceptionString(e));
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成密钥
     *
     * @return
     */
    private static Key genSecretKey() {
        LOGGER.debug("genSecretKey begin...");
        try {
            byte[] key = MD5Utils.getMD5ByteArray(KEY);
            byte[] salt = MD5Utils.getMD5ByteArray(SALT);
            List<byte[]> bytes = new ArrayList<>();
            bytes.add(key);
            bytes.add(salt);
            byte[] keyBytes = concat(bytes);
            SecretKey secretKey = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            LOGGER.debug("genSecretKey end...");
            return secretKey;
        } catch (OperationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字节数组拼接
     *
     * @param bytesList
     * @return
     */
    private static byte[] concat(List<byte[]> bytesList) {
        LOGGER.debug("concat begin...");
        if (!bytesList.isEmpty()) {
            int len = 0;
            for (byte[] bytes : bytesList) {
                if (bytes != null) {
                    len += bytes.length;
                }
            }
            byte[] destBytes = new byte[len];
            int destLen = 0;
            for (byte[] bytes : bytesList) {
                if (bytes != null) {
                    System.arraycopy(bytes, 0, destBytes, destLen, bytes.length);
                    destLen += bytes.length;
                }
            }
            return destBytes;
        }
        LOGGER.debug("concat end...");
        return new byte[0];
    }

}
