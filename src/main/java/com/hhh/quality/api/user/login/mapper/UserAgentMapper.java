package com.hhh.quality.api.user.login.mapper;

import com.hhh.quality.api.user.login.domain.UserAgent;
import org.apache.ibatis.annotations.Param;

public interface UserAgentMapper {
    /**
     * 根据账号获取用户
     * @param loginName 账号信息
     * @return
     */
    UserAgent selectByLoginName(@Param(value = "loginName") String loginName);

    /**
     * 添加用户登录信息
     * @param record
     * @return
     */
    int insertSelective(UserAgent record);

    /**
     * 根据主键获取登录信息
     * @param userAgentId
     * @return
     */
    UserAgent selectByPrimaryKey(@Param(value = "userAgentId") Long userAgentId);

    /**
     * 更新登陆次数及最新登陆时间
     * @param userAgentId
     * @param lastLoginTime
     * @return
     */
    int loginSuccess(@Param(value = "userAgentId") Long userAgentId,
        @Param(value = "lastLoginTime") String lastLoginTime);

    /**
     * 更新用户登录密码
     * @param userId
     * @param pwd
     * @return
     */
    int resetLoginPwd(@Param(value = "userId") Long userId, @Param(value = "pwd") String pwd);

    /**
     * 根据用户编号获取登录信息
     * @param userId
     * @return
     */
    UserAgent selectByUserId(@Param(value = "userId") Long userId);
}
