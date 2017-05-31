package com.example.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.*;
import com.example.utils.Func.Func1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 */
public class JsonHelper {
    private static Logger log = LoggerFactory.getLogger(JsonHelper.class);
    private static SerializeConfig mapping = new SerializeConfig();

    private static  ValueFilter valueFilter = null;
    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        valueFilter = null;
    }


    public static <T extends Object> String toJson(T t, String... includeFields) {
        if (t == null) return null;
        if (includeFields != null && includeFields.length > 0) {
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(t.getClass(), includeFields);
            return JSON.toJSONString(t, filter, SerializerFeature.PrettyFormat);//SerializerFeature.WriteMapNullValue 输出空值
        }

       return JSON.toJSONString(t, mapping, valueFilter, SerializerFeature.PrettyFormat);//SerializerFeature.WriteMapNullValue
       // return JSON.toJSONString(t, mapping, SerializerFeature.PrettyFormat);//SerializerFeature.WriteMapNullValue
    }

    public static <T> List<String> toJsonList(T... t) {
        return toJsonList(Arrays.asList(t));
    }

    public static <T> List<String> toJsonList(List<T> t) {
        return t.stream()
                .map(w -> JsonHelper.toJson(w))
                .collect(Collectors.toList());
    }

    public static <T> String[] toJsonArray(T... t) {
        return toJsonArray(Arrays.asList(t));
    }

    public static <T> String[] toJsonArray(List<T> t) {
        return t.stream()
                .map(w -> JsonHelper.toJson(w))
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    public static <T extends Object> List<T> listFromJson(String str) {
        List<T> stu = JSON.parseObject(str, new TypeReference<List<T>>() {
        });
        return stu;
    }

    public static JSONObject getJsonObjectFromJson(String str) {
        if (str == null) return null;
        return JSON.parseObject(str);
    }

    public static <T extends Object> T fromJson(String str, Class<T> cls) {
        if (str == null) return null;
        try {
            if ("s:4:\"true\";".equals(str)) {
                str += "sdfsd";
            }
            return JSON.parseObject(str, cls);
        } catch (Exception e) {
            //String fullStackTrace = ExceptionUtils.getFullStackTrace(e);
            log.error("参数转换错误，请求参数={},转换对象={}",str, cls.getSimpleName());
            return null;
        }
    }

    public static JSONObject fromJson(String str) {
        if (str == null) return null;
        try {
            return JSON.parseObject(str);
        } catch (Exception e) {
            //String fullStackTrace = ExceptionUtils.getFullStackTrace(e);
            //log.error(fullStackTrace);
            return null;
        }
    }


    public static Object[] array(Object... obgs) {
        return obgs;
    }

    public static Map<String, Object> jsonObj(Object... obgs) {
        if (obgs.length % 2 == 1)
            throw new RuntimeException("元素应该为偶数");
        Map<String, Object> resultMap = new HashMap<>();
        for (int i = 0; i < obgs.length; i += 2) {
            if (!(obgs[i] instanceof String)) {
                throw new RuntimeException("key应该是字符串" + obgs[i].toString());
            }
            String str = (String) obgs[i];
            Object obg = obgs[i + 1];
            if (obg instanceof Object[]) {

            }
            resultMap.put(str, obg);
        }
        return resultMap;
    }

    public static <T> Object[] jsonArray(List<T> item, Func1<T, Object> getObject) {
        List<Object> re = new ArrayList<>();
        item.forEach(w -> re.add(getObject.call(w)));
        return re.toArray();

    }

    /**
     * 转对象为字符，可选字段黑名单功能
     * 如果字段永远不可能输出为JSON字符串，
     * 可以添加 {@code @JSONField(serialize=false) } 到字符get方法或内部字段
     *
     * @param object      对象
     * @param blackFields 字段黑名单
     * @return
     */
    public static <T> String toJsonWithBlackFields(T object, String... blackFields) {
        Set<String> collect = Arrays.stream(blackFields).collect(Collectors.toSet());
        PropertyFilter filter = (object1, name, value) -> !collect.contains(name);
        return JSON.toJSONString(object, filter);
    }

    public static void setValueFilter(ValueFilter valueFilter) {
        JsonHelper.valueFilter = valueFilter;
    }
}
