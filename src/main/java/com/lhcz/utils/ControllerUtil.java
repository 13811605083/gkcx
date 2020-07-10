package com.lhcz.utils;

import com.lhcz.common.PageInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 41008
 */
@Slf4j
public class ControllerUtil {

    /**
     * 获取PageInfo对象，用于封装返回结果
     * @param request HttpServletRequest
     * @return PageInfo
     */
    public static PageInfo getPageInfo(HttpServletRequest request) {
        PageInfo pageInfo = new PageInfo();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String sord = request.getParameter("sord");
        String sidx = request.getParameter("sidx");
        if (!StringUtil.isNull(page)) {
            pageInfo.setPage(Integer.parseInt(page));
        }
        if (!StringUtil.isNull(limit)) {
            pageInfo.setLimit(Integer.parseInt(limit));
        }
        if (!StringUtil.isNull(sord)) {
            pageInfo.setSord(sord);
        }
        if (!StringUtil.isNull(sidx)) {
            pageInfo.setSidx(sidx);
        }
        return pageInfo;
    }

    /**
     * 将前台参数封装成Map
     * @param request HttpServletRequest
     * @return Map<String, Object>
     */
    public static Map<String, Object>getRequestParams(HttpServletRequest request){
        Map<String, Object> parameters = new HashMap<>(16);
        Map<String, String[]> reqParas = request.getParameterMap();
        for (Object o : reqParas.keySet()) {
            String key = (String) o;
            String[] values = reqParas.get(key);
            if (values == null) {
                parameters.put(key, "");
            } else if (values.length == 1) {
                String value = StringUtil.trim(values[0]);
                try {
                    parameters.put(key, java.net.URLDecoder.decode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage(),e);
                }
            } else {
                parameters.put(key, values);
            }
        }
        return parameters;
    }

}