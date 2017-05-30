package exception;

/**
 * 全部操作异常
 *
 * @author IceWee
 */
public class OperationException extends RuntimeException {

    private static final long serialVersionUID = -2748899876307860780L;
    private int code;
    private String message;

    public static final int AES_ENCRYPT_FAILED = 11; // AES-加密失败
    public static final int AES_DECRYPT_FAILED = 12; // AES-解密失败
    public static final int HTTP_REQUEST_FAILED = 21; // HTTP-请求失败
    public static final int BASE64_ENCODE_FAILED = 31; // BASE64-编码失败
    public static final int ZIP_UNCOMPRESS_FAILED = 41; // ZIP-解压失败
    public static final int ZIP_COMPRESS_FAILED = 42; // ZIP-压缩失败
    public static final int MD5_GENERATE_FAILED = 51; // MD5-生成失败
    public static final int CACHE_FILE_NOT_EXISTS = 61; // CACHE-缓存文件不存在
    public static final int CACHE_WRITE_FAILED = 62; // CACHE-保存缓存文件失败
    public static final int CACHE_READ_FAILED = 63; // CACHE-加载缓存文件失败
    public static final int JSON_FORMAT_FAILED = 71; // JSON-格式化失败
    public static final int CONFIG_FILE_NOT_EXISTS = 81; // CONFIG-配置文件不存在
    public static final int CONFIG_WRITE_FAILED = 82; // CONFIG-生成配置文件失败
    public static final int CONFIG_READ_FAILED = 83; // CONFIG-读取配置文件失败

    public OperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationException(String message) {
        super(message);
    }

    public OperationException(Throwable cause) {
        super(cause);
    }

    public OperationException(int code, Throwable cause) {
        this(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        switch (code) {
            case AES_ENCRYPT_FAILED:
                message = "AES-加密失败";
                break;
            case AES_DECRYPT_FAILED:
                message = "AES-解密失败";
                break;
            case HTTP_REQUEST_FAILED:
                message = "HTTP-请求失败";
                break;
            case BASE64_ENCODE_FAILED:
                message = "BASE64-编码失败";
                break;
            case ZIP_UNCOMPRESS_FAILED:
                message = "ZIP-解压失败";
                break;
            case ZIP_COMPRESS_FAILED:
                message = "ZIP-压缩失败";
                break;
            case MD5_GENERATE_FAILED:
                message = "MD5-生成失败";
                break;
            case CACHE_FILE_NOT_EXISTS:
                message = "CACHE-缓存文件不存在";
                break;
            case CACHE_WRITE_FAILED:
                message = "CACHE-保存缓存文件失败";
                break;
            case CACHE_READ_FAILED:
                message = "CACHE-加载缓存文件失败";
                break;
            case JSON_FORMAT_FAILED:
                message = "CACHE-JSON-格式化失败";
                break;
            case CONFIG_FILE_NOT_EXISTS:
                message = "CONFIG-配置文件不存在";
                break;
            case CONFIG_WRITE_FAILED:
                message = "CONFIG-生成配置文件失败";
                break;
            case CONFIG_READ_FAILED:
                message = "CONFIG-读取配置文件失败";
                break;
        }
        return message;
    }

}
