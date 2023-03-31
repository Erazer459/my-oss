package io.github.franzli347.foss.model.vo;

import cn.dev33.satoken.context.SaHolder;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.franzli347.foss.utils.IPUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @ClassName LoginRecord
 * @Author AlanC
 * @Date 2023/1/13 20:41
 **/
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_login_record")
public class LoginRecord {
    @TableId(type = IdType.INPUT)
    @Schema(description = "id")
    private int id;
    @Schema(description = "上次登录的时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime time;
    @Schema(description = "上次登录的IP地址")
    private String ip;
    @Schema(description = "用户id")
    private int uid;
    @Schema(description = "登录设备")
    private String device;
    @Schema(description = "城市")
    private String city;
}
