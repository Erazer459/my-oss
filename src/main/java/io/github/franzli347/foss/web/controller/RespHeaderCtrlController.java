package io.github.franzli347.foss.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RespHeaderCtrlController
 * @Author AlanC
 * @Date 2023/3/30 21:09
 **/
@RestController
@RequestMapping("/respHeaderCtrl")
@Tag(name="请求头控制模块")
public class RespHeaderCtrlController {

    @PostMapping("/add")
    @Operation(summary = "添加响应头")
    @Parameter(name = "respHeaderName",description = "自定义响应头名称")
    @Parameter(name = "diyRespHeaderArg",description = "自定义响应头参数(若是选择自定义(值为空)则需要输入自定义响应头名称,否则从参数列表中选择)")
    @Parameter(name = "arg",description = "响应头值")
    public boolean addRespHeader(String diyRespHeaderArg,String respHeaderName,String arg){
        return true;
    }
}
