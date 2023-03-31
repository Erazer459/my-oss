package io.github.franzli347.foss.web.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "运行测试")
@Validated
public class HelloController {
    @RequestMapping("/")
    public String env(){
        return "f-oss running";
    }

}
