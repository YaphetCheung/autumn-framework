package priv.autumn4j.framework.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        Properties properties = null;
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found!");
            }
            properties = new Properties();
            properties.load(is);

        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.error("close input stream failure", e);
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * 获取字符串属性
     *
     * @param properties
     * @param key
     * @return
     */
    public static String getString(Properties properties, String key) {
        if (properties.containsKey(key)) {
            return (String) properties.get(key);
        } else {
            return null;
        }
    }

    /**
     * 获取整型属性
     *
     * @param properties
     * @param key
     * @return
     */
    public static Integer getInteger(Properties properties, String key) {
        if (properties.containsKey(key)) {
            return (Integer) properties.get(key);
        } else {
            return null;
        }
    }

    /**
     * 获取浮点型属性
     *
     * @param properties
     * @param key
     * @return
     */
    public static Double getDouble(Properties properties, String key) {
        if (properties.containsKey(key)) {
            return (Double) properties.get(key);
        } else {
            return null;
        }
    }

    /**
     * 获取布尔型属性
     *
     * @param properties
     * @param key
     * @return
     */
    public static Boolean getBoolean(Properties properties, String key) {
        if (properties.containsKey(key)) {
            return (Boolean) properties.get(key);
        } else {
            return null;
        }
    }


}
