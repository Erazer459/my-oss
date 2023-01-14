package io.github.franzli347.foss.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.common.RedisConstant;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.mapper.FilesMapper;
import io.github.franzli347.foss.service.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;

/**
* @author FranzLi
* @description 针对表【tb_files】的数据库操作Service实现
* @createDate 2022-12-15 10:46:45
*/
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files>
    implements FilesService{


    @Value("${pathMap.source}")
    private String filePath;

    private StringRedisTemplate stringRedisTemplate;

    public FilesServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean removeFilesById(String id) {
        // 删除文件
        Files f = getById(id);
        String path = filePath + f.getPath();
        if(FileUtil.file(path).exists()){
            FileUtil.del(path);
        }
        stringRedisTemplate.opsForSet().remove(RedisConstant.FILE_MD5_LIST,f.getMd5());
        return removeById(id);
    }

    @Override
    public boolean updateFilesName(String id, String fileName) {
        Files f = getById(id);
        String path = filePath + f.getPath();
        // 修改文件名称
        // 修改数据库内容
        f.setFileName(fileName);
        f.setPath(f.getPath().substring(0,f.getPath().lastIndexOf("/")+1) + fileName);
        return FileUtil.file(path).renameTo(new File(fileName)) && updateById(f);
    }


}




