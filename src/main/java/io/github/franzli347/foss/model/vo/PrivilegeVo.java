package io.github.franzli347.foss.model.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName PrivilegeVo
 * @Author AlanC
 * @Date 2023/4/2 15:46
 **/
@Data
@Builder
public class PrivilegeVo {
    private int id;
    private int uid;
    private int bid;
    private String privilege;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String username;
}
