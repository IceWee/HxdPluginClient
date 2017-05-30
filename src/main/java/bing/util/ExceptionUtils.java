package bing.util;

/**
 * 异常工具类
 *
 * @author IceWee
 */
public class ExceptionUtils {

    /**
     * 由异常堆栈信息生成字符串
     *
     * @param e
     * @return
     */
    public static String createExceptionString(Exception e) {
        StringBuilder builder = new StringBuilder();
        builder.append(e.getMessage());
        builder.append('\t');
        for (int i = 0; i < e.getStackTrace().length; ++i) {
            builder.append(e.getStackTrace()[i]);
            builder.append('\n');
        }
        return builder.toString();
    }

}
