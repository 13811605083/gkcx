package com.lhcz.project.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.lhcz.common.PageInfo;
import com.lhcz.project.score.entity.ScoreInfo;
import com.lhcz.project.score.mapper.ScoreInfoMapper;
import com.lhcz.project.score.service.ScoreInfoService;
import com.lhcz.project.user.entity.UserInfo;
import com.lhcz.utils.ControllerUtil;
import com.lhcz.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 4100
 */
@Service
@Log4j2
public class ScoreInfoServiceImpl implements ScoreInfoService {

    @Resource
    private ScoreInfoMapper scoreInfoMapper;


    @Override
    public PageInfo getScoreInfoPage(HttpServletRequest request) {
        PageInfo pageInfo = ControllerUtil.getPageInfo(request);
        PageHelper.startPage(pageInfo.getPage(),pageInfo.getLimit());
        Example example = new Example(ScoreInfo.class);
        example.orderBy("years").desc();
        example.orderBy("ranking");
        Example.Criteria criteria = example.createCriteria();
        //封装检索条件
        StringUtil.resolverParameters( criteria, request);

        String rankingStart = request.getParameter("rankingStart");
        if(!StringUtil.isNull(rankingStart)){
            criteria.andGreaterThanOrEqualTo("ranking",rankingStart);
        }
        String rankingEnd = request.getParameter("rankingEnd");
        if(!StringUtil.isNull(rankingEnd)){
            criteria.andLessThanOrEqualTo("ranking",rankingEnd);
        }

        List<ScoreInfo> list = scoreInfoMapper.selectByExample(example);
        int count = scoreInfoMapper.selectCountByExample(example);
        pageInfo.setData(list);
        pageInfo.setCount(count);
        return pageInfo;
    }

}
