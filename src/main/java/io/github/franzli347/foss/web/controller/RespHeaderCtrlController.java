package io.github.franzli347.foss.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.franzli347.foss.model.entity.RespHeaderCtrl;
import io.github.franzli347.foss.web.service.RespHeaderCtrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * @ClassName RespHeaderCtrlController
 * @Author AlanC
 * @Date 2023/3/30 21:09
 **/
@RestController
@RequestMapping("/respHeaderCtrl")
@Tag(name="响应头控制模块")
public class RespHeaderCtrlController {
    private final RespHeaderCtrlService service;

    public RespHeaderCtrlController(RespHeaderCtrlService service) {
        this.service = service;
    }

    @PostMapping("/add")
    @Operation(summary = "添加响应头(填写respHeader,value,allowRepeat)")
    @Parameter(name = "allowRepeat",description = "是否允许重复")
    public boolean addRespHeader(@RequestBody RespHeaderCtrl respHeaderCtrl,boolean allowRepeat){
        if (!service.check(respHeaderCtrl.getRespheader())){
            throw new RuntimeException("响应头名称只能由大小写字母、短划线、数字组成，长度为1到100个字符，每个单词开头必须是大写字母");
        }
        respHeaderCtrl.setUid(StpUtil.getLoginIdAsInt());
        if (allowRepeat){
           return service.save(respHeaderCtrl);
        }
        return  service.cover(respHeaderCtrl);
    }
    @GetMapping("/getAll")
    @Operation(summary = "获取已设置的响应头")
    public IPage<RespHeaderCtrl> getAll(int page,int size){
        return service.getAll(page,size);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除响应头数据")
    public boolean delete(int id){
        return service.removeById(id);
    }
    @PostMapping("/update")
    @Operation(summary = "更新响应头数据")
    public boolean update(@RequestBody RespHeaderCtrl respHeaderCtrl){
        if (!service.check(respHeaderCtrl.getRespheader())){
            throw new RuntimeException("响应头名称只能由大小写字母、短划线、数字组成，长度为1到100个字符，每个单词开头必须是大写字母");
        }
        respHeaderCtrl.setUid(StpUtil.getLoginIdAsInt());
        return service.updateById(respHeaderCtrl);
    }
}
