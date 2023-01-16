package io.github.franzli347.foss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.entity.Files;

/**
* @author FranzLi
* @description 针对表【tb_files】的数据库操作Service
* @createDate 2022-12-15 10:46:45
*/
public interface FilesService extends IService<Files> {

    boolean removeFilesById(String id, String bid);

}
