package me.turingteam.turingoss.controller;

import me.turingteam.turingoss.common.Result;
import me.turingteam.turingoss.common.ResultCode;
import me.turingteam.turingoss.utils.FastDFSClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/fastdfs")
public class FastDFSController {
    @RequestMapping("hello")
    public Result<String[]> test(MultipartFile file) throws IOException {
        return null;
    }

}
