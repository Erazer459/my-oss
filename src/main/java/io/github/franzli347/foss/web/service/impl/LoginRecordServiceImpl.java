package io.github.franzli347.foss.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.model.vo.LoginRecord;
import io.github.franzli347.foss.web.service.LoginRecordService;
import io.github.franzli347.foss.web.mapper.LoginRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【tb_login_record】的数据库操作Service实现
* @createDate 2023-03-31 19:06:43
*/
@Service
public class LoginRecordServiceImpl extends ServiceImpl<LoginRecordMapper, LoginRecord>
    implements LoginRecordService{

}




