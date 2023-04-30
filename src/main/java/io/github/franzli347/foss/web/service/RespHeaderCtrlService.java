package io.github.franzli347.foss.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.model.entity.RespHeaderCtrl;

public interface RespHeaderCtrlService extends IService<RespHeaderCtrl> {
    IPage<RespHeaderCtrl> getAll(int page, int size);
    boolean check(String respHeader);

    boolean cover(RespHeaderCtrl respHeaderCtrl);
}
