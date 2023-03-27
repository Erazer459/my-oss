package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @ClassName SysUser
 * @Author AlanC
 * @Date 2023/1/8 15:43
 **/
@Data
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@TableName("tb_sysuser")
public class SysUser extends UserBase{
    /**
     * 密码
     */
    @Schema(description = "密码")
    @TableField("password")
    private String password;
    /**
     * 盐值
     */
    @Hidden
    @TableField("salt")
    @Builder.Default
    private String salt="turing_oss";

    public UserBase toUserBase(){
        return UserBase.builder()
                .email(this.getEmail())
                .id(this.getId())
                .username(this.getUsername())
                .build();
    }
}
