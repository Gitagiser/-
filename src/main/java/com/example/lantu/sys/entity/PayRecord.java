package com.example.lantu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizhenxiang
 * @since 2023-09-17
 */
@TableName("x_pay_record")
//@ApiModel(value = "PayRecord对象", description = "")
public class PayRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty("金额")
    private BigDecimal amount;

//    @ApiModelProperty("收支情况")
    private Integer incomeExpenditure;

//    @ApiModelProperty("删除状态")
    private Integer isDelete;

//    @ApiModelProperty("创建时间")
    private LocalDate createTime;

    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Integer getIncomeExpenditure() {
        return incomeExpenditure;
    }

    public void setIncomeExpenditure(Integer incomeExpenditure) {
        this.incomeExpenditure = incomeExpenditure;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "PayRecord{" +
            "id=" + id +
            ", amount=" + amount +
            ", incomeExpenditure=" + incomeExpenditure +
            ", isDelete=" + isDelete +
            ", createTime=" + createTime +
            ", avatar=" + avatar +
        "}";
    }
}
