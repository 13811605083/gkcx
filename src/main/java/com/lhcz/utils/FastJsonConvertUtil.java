package com.lhcz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 41008
 */
@Slf4j
public class FastJsonConvertUtil {

    private static final SerializerFeature[] FEATURES = {
            SerializerFeature.WriteMapNullValue
            ,SerializerFeature.WriteNullListAsEmpty
            ,SerializerFeature.WriteNullStringAsEmpty
//            ,SerializerFeature.WriteNullNumberAsZero
//            ,SerializerFeature.WriteNullBooleanAsFalse
    };

    /**
     * <B>将JSON字符串转成实体对象</B>
     * @param data json数据
     * @param clazz 转换对象
     * @return T
     */
    public static <T> T json2Obj(String data,Class<T> clazz){
        try{
            return JSON.parseObject(data,clazz);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * <B>将JSON对象转成实体对象</B>
     * @param data JSONObject数据
     * @param clazz 转换对象
     * @return T
     */
    public static <T> T jsonObj2Obj(JSONObject data, Class<T> clazz){
        try{
            return JSONObject.toJavaObject(data,clazz);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * <B>将JSON字符串组转成实体对象组</B>
     * @param data json数据组
     * @param clazz 转换对象
     * @return T
     */
    public static <T> List<T> json2Array(String data, Class<T> clazz){
        try{
            return JSON.parseArray(data,clazz);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * <B>将JSON对象组转成实体对象组</B>
     * @param data json对象组数据
     * @param clazz 转换对象
     * @return List<T>
     */
    public static <T> List<T> jsonArray2Array(List<JSONObject> data, Class<T> clazz){
        try{
            List<T> t = new ArrayList<>();
            for(JSONObject jsonObject : data){
                t.add(JSONObject.toJavaObject(jsonObject,clazz));
            }
            return t;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * <B>将对象转换为JSON字符串(不包括null属性)</B>
     * @param object Object
     * @return String
     */
    public static String obj2JsonWithoutNull(Object object){
        try{
            return JSON.toJSONString(object);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * <B>将对象转换为JSON对象</B>
     * @param object Object
     * @return JSONObject
     */
    public static JSONObject obj2JsonObj(Object object){
        try{
            return (JSONObject)JSONObject.toJSON(object);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * <B>将对象转换为JSON对象</B>
     * @param object Object
     * @return JSONObject
     */
    public static JSONObject obj2JsonObj(Object object,String format){
        try{
            String s = JSON.toJSONStringWithDateFormat(object,format);
            return JSONObject.parseObject(s);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * <B>将对象转换为JSON字符串(包括null属性)</B>
     * @param object Object
     * @return String
     */
    public static String obj2JsonWithNull(Object object){
        try{
           return JSON.toJSONString(object,FEATURES);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * <B>将对象转换为JSON字符串(包括null属性)</B>
     * <B>日期对象转成格式：yyyy-MM-dd HH:mm:ss</B>
     * @param object Object
     * @return String
     */
    public static String obj2JsonWithDateFormat(Object object){
        try{
            return JSON.toJSONStringWithDateFormat(object,JSON.DEFFAULT_DATE_FORMAT, FEATURES);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * <B>将对象转换为JSON字符串(不包括null属性)</B>
     * @param object Object
     * @param format 日期对象转成格式例如：yyyy-MM-dd
     * @return String
     */
    public static String obj2JsonWithDateFormat(Object object,String format){
        try{
            return JSON.toJSONStringWithDateFormat(object,format);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }
}