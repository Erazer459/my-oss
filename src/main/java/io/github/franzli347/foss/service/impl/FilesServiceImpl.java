package io.github.franzli347.foss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.mapper.FilesMapper;
import io.github.franzli347.foss.service.FilesService;
import org.springframework.stereotype.Service;

/**
* @author FranzLi
* @description 针对表【tb_files】的数据库操作Service实现
* @createDate 2022-12-15 10:46:45
*/
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files>
    implements FilesService{

}




