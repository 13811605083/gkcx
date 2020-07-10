package com.lhcz.utils;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 41008
 */
@Slf4j
public class StringUtil {

    private static final String DOT = ".";
    private static final String ZERO = "0";
    private static final DecimalFormat DF = new DecimalFormat("#,###.00");

    /**
     * 判断字符串组是否包含字符串
     * @param array String[]
     * @param value String
     * @return boolean
     */
    public static boolean contains(String[] array ,String value){
        for(String s : array){
            if(s.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断日期格式是否正确
     * @param str
     * @return
     */
    public static boolean isDate(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            sdf.parse(str).toString();
            return true;
        }catch (ParseException e){
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 判断是否是正整数
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串组是否模糊包含字符串
     * @param array String[] array
     * @param value String
     * @return boolean
     */
    public static boolean containsFuzzy(String[] array ,String value){
        if(isNull(value)) {
            return false;
        }
        for(String s : array){
            if(value.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     * @param str String
     * @return String
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str.trim());
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 判断字符串是否在数组中
     * @param key String
     * @param array String[]
     * @return boolean
     */
    public static boolean inArray(String key,String[] array){
        if(key == null || array == null) {
            return false;
        }
        for(String s : array){
            if(key.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**将boolean值转成0和1*/
    public static String formatBoolean(Boolean b){
        if(b) {
            return "1";
        }
        return "0";
    }

    /**
     * 在常规trim方法加入对null值的String类型的处理.
     * @param str Object
     * @return String
     */
    public static String trim(Object str){
        if(str == null) {
            return "";
        }
        String s = String.valueOf(str);
        return s.trim();
    }

    /**
     * 去掉小数点后面尾零.
     * @param str String
     * @return String
     */
    public static String trimZeroEnds(String str){
        String s = trim(str);
        if(!s.contains(DOT)){
            return s;
        }else{
            if(s.endsWith(ZERO)){
                return trimZeroEnds(s.substring(0,s.length()-1));
            }else if(s.endsWith(DOT)){
                return trimZeroEnds(s.substring(0,s.length()-1));
            }
            return s;
        }
    }

    /**
     * 去掉小数点后面尾零.并加上逗号显示
     * @param value BigDecimal
     * @return String
     */
    public synchronized static String trimZeroEnds(BigDecimal value){
        if(value == null){
            return "";
        }
        if(value.floatValue() == 0F){
            return ZERO;
        }
        return trimZeroEnds(DF.format(value));
    }

    /**
     * 把字符串数组转换为'xx','yy'格式.
     * @param array Object[]
     * @return 'xx','yy'
     */
    public static String getSqlCaseParam(Object[] array){
        StringBuilder hql = new StringBuilder();
        if(array == null || array.length==0) {
            return "''";
        }
        for(Object str : array){
            if(hql.toString().toString().length() > 0) {
                hql.append(",");
            }
            hql.append("'").append(str.toString()).append("'");
        }
        return hql.toString();
    }

    /**
     * 把字符串xx,yy组转换为'xx','yy'格式.
     * @return 'xx','yy'
     */
    public static String getSqlCaseParam(String splitParam){
        if(StringUtil.trim(splitParam).length()==0) {
            return "''";
        }
        String[] array = splitParam.split(",");
        return getSqlCaseParam(array);
    }

    /**
     * 转化为 a,b,c格式
     * @param set Set<String>
     * @return a,b,c
     */
    public static String formatSet(Set<String> set){
        if(set == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for(String s : set){
            if(result.length() == 0){
                result.append(s);
            }else{
                result.append(",").append(s);
            }
        }
        return result.toString();
    }

    /**
     * 转化为 a,b,c格式
     * @param list List<String>
     * @return a,b,c
     */
    public static String formatList(List<String> list){
        if(isNull(list)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for(String s : list){
            if(result.length() == 0){
                result.append(s);
            }else{
                result.append(",").append(s);
            }
        }
        return result.toString();
    }

    /**
     * 转化为 'a','b','c'格式
     * @param set Set<String>
     * @return 'a','b','c'
     */
    public static String formatSetForSql(Set<String> set){
        if(set == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for(String s : set){
            if(result.length() == 0){
                result.append("'").append(s).append("'");
            }else{
                result.append(",'").append(s).append("'");
            }
        }
        return result.toString();
    }

    /**
     * 判断列表是否为空
     * @param list List
     * @return boolean
     */
    public static boolean isNull(List list){
        return list == null || list.size() == 0;
    }

    /**
     * 判断字符串是否无效
     * @param arg String
     * @return boolean
     */
    public static boolean isNull(String arg){
        return arg == null || "".equals(arg) || "null".equals(arg) || "undefined".equals(arg) || "NaN".equals(arg) || "Infinity".equals(arg) || "-Infinity".equals(arg);
    }

    /**
     * 将流读成字符串
     * @param is InputStream
     * @return String
     */
    public synchronized static String convertStreamToString(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            if(StandardCharsets.UTF_8.name().equals(getEncoding(sb.toString()))){
                return sb.toString();
            }
            return URLDecoder.decode(sb.toString(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        return "";
    }

    private static String getEncoding(String str) throws Exception {
        String encode = "UTF-8";
        if (str.equals(new String(str.getBytes(), encode))){
            return encode;
        }
        encode = "UTF-16";
        if (str.equals(new String(str.getBytes(), encode))){
            return encode;
        }
        encode = "ASCII";
        if (str.equals(new String(str.getBytes(), encode))){
            return encode;
        }
        encode = "ISO-8859-1";
        if (str.equals(new String(str.getBytes(), encode))) {
            return encode;
        }
        encode = "GB2312";
        if (str.equals(new String(str.getBytes(), encode))) {
            return encode;
        }
        return "未识别编码格式";
    }

    /**字符串转UTF-8编码*/
    public static String decode(String arg) {
        if(isNull(arg)) {
            return "";
        }
        try {
            arg = URLDecoder.decode(arg, "UTF-8");
            arg = URLDecoder.decode(arg, "UTF-8");
            return arg;
        } catch (Exception ignored) {
        }
        return arg;
    }

    /**判断对象是否为空*/
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * 解析查询参数
     * @param criteria Example.Criteria
     * @param request HttpServletRequest
     */
    public static void resolverParameters(Example.Criteria criteria, HttpServletRequest request){
        Map<String, String[]> reqParas = request.getParameterMap();
        for (Object o : reqParas.keySet()) {
            String key = (String) o;
            String[] values = reqParas.get(key);
            if (values != null) {
                String value = StringUtil.trim(values[0]);
                if(value.length() == 0){
                    continue;
                }
                try {
                    value = java.net.URLDecoder.decode(value, "UTF-8");
                    if(key.startsWith("s_")){
                        //模糊查询
                        criteria.andLike( key.substring("s_".length()),"%"+value+"%");
                    }
                    if(key.startsWith("ns_")){
                        //模糊查询
                        criteria.andNotLike( key.substring("ns_".length()),"%"+value+"%");
                    }
                    if(key.startsWith("seq_")){
                        //完全等于的查询条件，如部门id，数据字典
                        criteria.andEqualTo( key.substring("seq_".length()), value);
                    }
                    if(key.startsWith("nseq_")){
                        //完全不等于的查询条件，如部门id，数据字典
                        criteria.andNotEqualTo( key.substring("nseq_".length()), value);
                    }
                    if(key.indexOf("sle_") == 0) {
                        //小于查询条件，如时间，金额
                        criteria.andLessThan(key.substring("sle_".length()), value);
                    }
                    if(key.indexOf("sge_") == 0) {
                        //大于查询条件，如时间，金额
                        criteria.andGreaterThan(key.substring("sge_".length()), value);
                    }
                    if(key.indexOf("sleo_") == 0) {
                        //小于等于查询条件，如时间，金额
                        criteria.andLessThanOrEqualTo(key.substring("sleo_".length()), value);
                    }
                    if(key.indexOf("sgeo_") == 0) {
                        //大于等于查询条件，如时间，金额
                        criteria.andGreaterThanOrEqualTo(key.substring("sgeo_".length()), value);
                    }
                    if(key.indexOf("sin_") == 0) {
                        //包含多个值
                        criteria.andIn(key.substring("sin_".length()), Arrays.asList(value.split(",")));
                    }
                    if(key.indexOf("nsin_") == 0) {
                        //排除多个值
                        criteria.andNotIn(key.substring("nsin_".length()), Arrays.asList(value.split(",")));
                    }
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
}