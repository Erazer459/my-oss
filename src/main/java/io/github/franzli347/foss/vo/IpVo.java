package io.github.franzli347.foss.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName IpVo
 * @Author AlanC
 * @Date 2023/1/14 15:27
 **/
@Data
@Builder
public class IpVo implements Serializable{
    @Schema(description = "状态")
    private String status;
    @Schema(description = "地区名")
    private String regionName;
    @Schema(description = "城市")
    private String city;
    @Schema(description = "国家")
    private String country;
    @Schema(description = "经度")
    private String lon;
    @Schema(description = "纬度")
    private String lat;
    public String getLocationInfo(){
        return this.regionName+this.city;
    }
}