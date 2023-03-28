package io.github.franzli347.foss.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @ClassName ShareAuth
 * @Author AlanC
 * @Date 2023/3/27 20:19
 **/
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_auth_keys")
public class AuthKeys {
    @Schema(description = "id")
    @TableId(type=IdType.AUTO)
    private int id;
    @Schema(description = "accesskey,固定的,不会随意变动")
    private String accesskey;
    @Schema(description = "secretkey,可随时更新以防泄露")
    private String secretkey;
    @Schema(description = "用户id")
    private int uid;
}
