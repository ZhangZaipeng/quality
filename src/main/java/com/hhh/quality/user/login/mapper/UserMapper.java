package com.hhh.quality.user.login.mapper;

import com.hhh.quality.user.login.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 添加用户信息
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 根据用户编号获取用户信息
     * @param userId
     * @return
     */
    User findUserById(@Param(value = "userId") Long userId);

    /**
     * 根据用户昵称获取用户信息
     * @param userName
     * @return
     */
    User findUserByUserName(@Param(value = "userName") String userName);

    User selectUserByMobile(@Param(value = "mobile") String mobile);

    int updateUser(@Param(value = "userId") Long userId, @Param(value = "uname") String uname,
        @Param(value = "iconImgUrl") String iconImgUrl);

}
