package com.lhcz.project.score.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * user_info
 * @author 4100
 */
@Data
@Table(name="lp_score_info")
public class ScoreInfo implements Serializable {


    @Id
    private int id;

    /**年份*/
    private String years;

    /**名次*/
    private Integer ranking;

    /**姓名*/
    private String names;

    /**性别*/
    private String gender;

    /**分数*/
    private String scores;


}