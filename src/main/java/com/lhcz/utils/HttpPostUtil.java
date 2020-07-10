package com.lhcz.utils;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author 41008
 */
@Slf4j
public class HttpPostUtil {

	/**请求超时时间10秒--测试时使用*/
	private final static int TIME_OUT = 10000;
	
	/**
     * 向指定URL发送GET方法的请求
     * @param url  发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
		if (StringUtil.isNull(url)) {
			return null;
		}
		HttpURLConnection connection = null;
		try {
			long time = System.currentTimeMillis();
			String urlNameString = url + "?time=" + time + "&" + param;
			//打开和URL之间的连接
			connection = (HttpURLConnection) new URL(urlNameString).openConnection();
			connection.setUseCaches(false);
			//设置建立连接的超时时间
			connection.setConnectTimeout(TIME_OUT);
			//设置传递数据的超时时间
			connection.setReadTimeout(2000);
			//自动处理重定向
			connection.setInstanceFollowRedirects(true);
			// 设置请求方式
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Charset", "utf-8");
			//接收任意资源
			connection.setRequestProperty("Accept", "*/*");
			//设置发送数据格式
			connection.setRequestProperty("Content-Type", "application/text;charset=utf-8");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			@Cleanup
			InputStream is = connection.getInputStream();
			return StringUtil.convertStreamToString(is);
		} catch (Exception e) {
			 log.error(e.getMessage(),e);
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
    }
    
    /**
	 * 发送HttpPost请求
	 * @param url 服务地址
	 * @param params json字符串,
	 * @return 成功:返回json字符串<br/>
	 */
    public static String sendPost(String url, String params) {
		if(StringUtil.isNull(url)) {
			return null;
		}
        try {
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			//设置建立连接的超时时间
            connection.setConnectTimeout(TIME_OUT);
			//设置传递数据的超时时间
			connection.setReadTimeout(TIME_OUT);
			//自动处理重定向
			connection.setInstanceFollowRedirects(true);
			// 设置请求方式
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset","utf-8");
			//接收任意资源
            connection.setRequestProperty("Accept", "*/*");
			//设置发送数据格式
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 获取URLConnection对象对应的输出流
			@Cleanup
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
			//发送请求参数
            out.write(params);
			// flush输出流的缓冲
            out.flush();
            // 定义 BufferedReader输入流来读取URL的响应
			@Cleanup
            InputStream is = connection.getInputStream();
            return StringUtil.convertStreamToString(is);
        }catch (Exception e) {
        	 log.error("【"+url+"】【"+e.getMessage()+"】",e);
			return null;
        }
	}
}