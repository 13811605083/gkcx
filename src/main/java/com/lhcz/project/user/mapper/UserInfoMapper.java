package com.lhcz.project.user.mapper;

import com.lhcz.common.MyBaseMapper;
import com.lhcz.project.user.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 41008
 */
public interface UserInfoMapper extends MyBaseMapper<UserInfo> {

    /**
     * 通过账号获取用户信息
     * @param userAccount 账号
     * @return UserInfo
     */
    @Select("SELECT * FROM lp_user_info where user_account=#{userAccount}")
    UserInfo selectByUserAccount(String userAccount);

    /**
     * 判断账号是否存在
     * @param account 账号
     * @return int
     */
    @Select("SELECT COUNT(user_id) FROM lp_user_info where user_account = #{account}")
    int getCountByAccount(String account);

    /**
     * 批量删除
     * @param userId ID, password 密码
     * @return Boolean
     */
    @Select("select COUNT(user_id) from lp_user_info where user_id=#{userId} and user_pwd=#{password} ")
    Boolean checkPwd(@Param("userId") String userId,@Param("password") String password);
}