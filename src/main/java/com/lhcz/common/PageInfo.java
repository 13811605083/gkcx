package com.lhcz.common;

import com.lhcz.utils.StringUtil;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * @author 41008
 */
@Data
public class PageInfo implements Serializable {

    /**排序倒序*/
    public static final String DESC = "desc";
    /**排序正序*/
    public static final String ASC = "asc";

    /**记录每页显示数据条数*/
    private Integer limit = 0;
    /**记录当前的页码*/
    private Integer page = 0;
    /**排序 asc or desc*/
    private String	sord = "";
    /**排序的列名*/
    private String	sidx = "";


    /**成功的状态码*/
    private Integer code = 0;
    /**状态信息的字段名称*/
    private String msg = "";
    /**记录总数据条数*/
    private Integer count = 0;
    /**用于当前页显示的数据记录集合JSON*/
    private List data;

    public PageInfo() { }

    public PageInfo(int page, int limit, String sord, String sidx){
        this.page = page;
        this.limit = limit;
        this.sord = sord;
        this.sidx = sidx;
    }

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

}
