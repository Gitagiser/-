package com.example.lantu.sys.mapper;

import com.example.lantu.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizhenxiang
 * @since 2023-02-18
 */
public interface UserMapper extends BaseMapper<User> {
    public List<String> getRoleNamesByUserId(Integer userId);
}
