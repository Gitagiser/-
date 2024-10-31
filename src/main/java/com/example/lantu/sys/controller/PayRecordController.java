package com.example.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lantu.common.vo.Result;
import com.example.lantu.sys.entity.PayRecord;
import com.example.lantu.sys.entity.User;
import com.example.lantu.sys.service.IPayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizhenxiang
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/payRecord")
public class PayRecordController {
    @Autowired
    private IPayRecordService payRecordService;
//    private Result result;

    @GetMapping("/all")
    public Result<List<PayRecord>> getAllUser() {
        List<PayRecord> list = payRecordService.list();
        return Result.success(list,"查询成功");
    }


    @GetMapping("/list")
    public Result<Map<String, Object>> getPayListPage(@RequestParam(value = "incomeExpenditure", required = false) int incomeExpenditure,
                                                       @RequestParam(value = "createTime", required = false) String createTime,
                                                       @RequestParam("pageNo") Long pageNo,
                                                       @RequestParam("pageSize") Long pageSize) {

        LambdaQueryWrapper<PayRecord> wrapper = new LambdaQueryWrapper();
        wrapper.eq(PayRecord::getIncomeExpenditure,incomeExpenditure);
        if(StringUtils.hasLength(createTime)){
            LocalDate beginDateTime = LocalDate.parse(createTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            wrapper.eq(StringUtils.hasLength(createTime),PayRecord::getCreateTime,beginDateTime);
        }
        wrapper.orderByDesc(PayRecord::getId);

        Page<PayRecord> page = new Page<>(pageNo, pageSize);

        payRecordService.page(page, wrapper);
        Map<String, Object> data = new HashMap<>();
//        Double totalMoney = page.stream().map(Apple::getMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);
    }

    @PostMapping("/add")
    public Result<?> addPayRecord(@RequestBody PayRecord PayItem){
        payRecordService.save(PayItem);
        return Result.success("新增成功");
    }

    @GetMapping("/find/{id}")
    public Result<?> findPayRecordById(@PathVariable("id") Integer id){
        PayRecord user = payRecordService.getById(id);
        return Result.success(user);
    }

    @PostMapping("/updata")
    public Result<?> upDataPayRecord(@RequestBody PayRecord PayItem){
//        user.setPassword(null);
        payRecordService.updateById(PayItem);
        return Result.success("修改成功");
    }

    @PostMapping("/delete")
    public Result<?> deletePayRecord(@RequestBody PayRecord PayItem){
        payRecordService.removeById(PayItem);
        return Result.success("删除成功");
    }
}
