package io.github.franzli347.foss.web.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.model.entity.RespHeaderCtrl;
import io.github.franzli347.foss.model.vo.LoginRecord;
import io.github.franzli347.foss.web.mapper.RespheaderCtrlMapper;
import io.github.franzli347.foss.web.service.RespHeaderCtrlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RespHeaderCtrlServiceImpl
 * @Author AlanC
 * @Date 2023/4/1 16:25
 **/
@Service
public class RespHeaderCtrlServiceImpl extends ServiceImpl<RespheaderCtrlMapper, RespHeaderCtrl> implements RespHeaderCtrlService {
    @Override
    public IPage<RespHeaderCtrl> getAll(int page, int size) {
        IPage<RespHeaderCtrl> p=new Page<>(page,size);
        page(p,new LambdaQueryWrapper<RespHeaderCtrl>().eq(RespHeaderCtrl::getUid, StpUtil.getLoginIdAsInt()));
        return p;
    }

    @Override
    public boolean check(String respHeader) {
        // 验证字符串长度为1到100个字符
        if (respHeader == null || respHeader.length() < 1 || respHeader.length() > 100) {
            return false;
        }
        // 验证字符串是否符合HTTP响应头命名规则
        String regex = "^[A-Z][a-zA-Z0-9\\-]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(respHeader);
        return matcher.matches();
    }

    @Override
    @Transactional
    public boolean cover(RespHeaderCtrl respHeaderCtrl) {
        remove(new LambdaQueryWrapper<RespHeaderCtrl>().eq(RespHeaderCtrl::getRespheader,respHeaderCtrl.getRespheader()).eq(RespHeaderCtrl::getUid,respHeaderCtrl.getUid()));//不允许重复则删除其他
        return save(respHeaderCtrl);
    }
}
