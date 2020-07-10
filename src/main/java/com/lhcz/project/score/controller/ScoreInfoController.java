package com.lhcz.project.score.controller;


import com.lhcz.common.PageInfo;
import com.lhcz.project.score.service.ScoreInfoService;
import com.lhcz.utils.FastJsonConvertUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 41008
 */
@Controller
@Log4j2
@RequestMapping("/score")
public class ScoreInfoController {

    @Resource
    private ScoreInfoService scoreInfoService;

    /**用户列表数据*/
    @ResponseBody
    @RequestMapping(value = "/listData")
    public String listData(HttpServletRequest request){
        try {
            PageInfo pageInfo = scoreInfoService.getScoreInfoPage(request);
            return FastJsonConvertUtil.obj2JsonWithDateFormat(pageInfo);
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**通用跳转*/
    @GetMapping(value = "/to{path}")
    public String path(@PathVariable("path")String path){
        return "score/score_"+path.toLowerCase();
    }


}
