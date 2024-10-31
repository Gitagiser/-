package com.example.lantu.sys.service.impl;

//import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lantu.sys.entity.User;
import com.example.lantu.sys.mapper.UserMapper;
import com.example.lantu.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import com.example.lantu.common.vo.utils.JwtUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhenxiang
 * @since 2023-02-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Map<String, Object> login(User user) {
        //根据用户名和密码查询

        //结果不为空，则生成token
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);
        if(loginUser != null){
            // 暂时用UUID
//            String key = "user:" + UUID.randomUUID();
            String key = jwtUtil.createToken(wrapper);
//            loginUser.setPassword(null);

            //存入redis
            //redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            return data;
        }
        return null;
    }

    public Map<String, Object> getUserInfo(String token) {
        // 从redis查询token
//         Object obj = redisTemplate.opsForValue().get(token);
        // 反序列化
//        User user = JSON.parseObject(JSON.toJSONString(user),User.class);
        User loginUser = null;
        try{
            loginUser = jwtUtil.parseToken(token,User.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(loginUser != null){
            Map<String, Object> data =  new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());
            List<String> roleList = this.baseMapper.getRoleNamesByUserId(loginUser.getId());
            data.put("roles", roleList);
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        // redisTemplate.delete(token);
    }
}
