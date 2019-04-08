package http;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import net.sf.json.JSONObject;

public class SybUtil {
    /**
     * js转化为实体
     *
     * @param <T>     Bean泛型
     * @param jsonstr json字符串
     * @param cls     要转换的Bean
     * @return 转化好的Bean
     */
    public static <T> T json2Obj(String jsonstr, Class<T> cls) {
        JSONObject jo = JSONObject.fromObject(jsonstr);
        T obj = (T) JSONObject.toBean(jo, cls);
        return obj;
    }
}
