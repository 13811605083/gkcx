package com.lhcz.project.score.service;

import com.lhcz.common.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bmy
 */
public interface ScoreInfoService {


    /**
     * 获取用户列表信息
     * @param request HttpServletRequest
     * @return PageInfo
     */
    PageInfo getScoreInfoPage(HttpServletRequest request);

}
