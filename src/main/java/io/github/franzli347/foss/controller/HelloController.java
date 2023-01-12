package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@RestController
@Tag(name = "运行测试")
@Validated
public class HelloController {
    @RequestMapping("/")
    public String env(){
        return "f-oss running";
    }

}
