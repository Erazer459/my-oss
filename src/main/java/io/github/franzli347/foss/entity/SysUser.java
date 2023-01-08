package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName SysUser
 * @Author AlanC
 * @Date 2023/1/8 15:43
 **/
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_sysuser")
public class SysUser extends UserBase{
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 盐值
     */
    @Hidden
    @TableField("salt")
    private String salt;

    //Role
}
