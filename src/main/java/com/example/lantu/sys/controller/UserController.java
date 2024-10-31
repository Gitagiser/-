package com.example.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lantu.common.vo.Result;
import com.example.lantu.config.MyCorsConfig;
import com.example.lantu.sys.entity.User;
import com.example.lantu.sys.service.IUserService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizhenxiang
 * @since 2023-02-18
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
//    private Result result;

    @GetMapping("/all")
    public Result<List<User>> getAllUser() {
        List<User> list = userService.list();
        return Result.success(list,"查询成功");
    }

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data = userService.login(user);

        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或密码错误");
    }


    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token) {
        List<User> list = userService.list();
        //根据token获取用户信息，redis
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20003,"登陆失败");
//        return Result.success(list,"查询成功");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getUserListPage(@RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "phone", required = false) String phone,
                                     @RequestParam("pageNo") Long pageNo,
                                     @RequestParam("pageSize") Long pageSize) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StringUtils.hasLength(username), User::getUsername, username);
        wrapper.eq(StringUtils.hasLength(phone), User::getPhone, phone);
        wrapper.orderByDesc(User::getId);

        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user){
        userService.save(user);
        return Result.success("新增用户成功");
    }

    @GetMapping("/find/{id}")
    public Result<?> findUserById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @PutMapping("/updata")
    public Result<?> upDataUser(@RequestBody User user){
        user.setPassword(null);
        userService.updateById(user);
        return Result.success("修改成功");
    }

    @DeleteMapping("/delete")
    public Result<?> deleteUser(@RequestBody User user){
        userService.removeById(user);
        return Result.success("删除成功");
    }

}
