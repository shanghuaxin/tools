package com.wangzhixuan.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.User;
import com.wangzhixuan.model.vo.UserVo;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface UserService extends IService<User> {

    List<User> selectByLoginName(UserVo userVo);

    void insertByVo(UserVo userVo);

    UserVo selectVoById(Long id);

    void updateByVo(UserVo userVo);

    void updatePwdByUserId(Long userId, String md5Hex);

    PageInfo selectDataGrid(PageInfo pageInfo);

    void deleteUserById(Long id);
}