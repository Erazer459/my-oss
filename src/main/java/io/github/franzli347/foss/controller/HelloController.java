package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "运行测试")
public class HelloController {

    @RequestMapping("/")
    public Result env(){
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .msg("f-oss running")
                .build();
    }


}
