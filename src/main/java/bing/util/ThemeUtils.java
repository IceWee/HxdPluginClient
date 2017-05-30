package bing.util;

import bing.Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * 主题工具类
 *
 * @author IceWee
 */
public class ThemeUtils {

    /**
     * 保存自定义主题
     *
     * @param shortThemeName
     */
    public static void saveCustomTheme(String shortThemeName) {
        PropertiesUtils.updateProperty(Constants.CONFIG_KEY_THEME, shortThemeName);
    }

    /**
     * 获取默认主题类全名
     *
     * @return
     */
    public static String getDefaultThemeFullName() {
        return getThemeFullName(Constants.THEME_DEFAULT);
    }

    /**
     * 获取用户自定义主题名称
     *
     * @return
     */
    public static String getCustomThemeFullName() {
        String themeName = PropertiesUtils.getProperty(Constants.CONFIG_KEY_THEME);
        if (StringUtils.isBlank(themeName)) {
            return StringUtils.EMPTY;
        }
        return getThemeFullName(themeName);
    }

    /**
     * 获取主题类全名
     *
     * @param themeName
     * @return
     */
    public static String getThemeFullName(String themeName) {
        return Constants.THEME_PREFIX + themeName;
    }

}
