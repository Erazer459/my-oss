package io.github.franzli347.foss.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.common.RedisConstant;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.mapper.FilesMapper;
import io.github.franzli347.foss.service.BucketService;
import io.github.franzli347.foss.service.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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

    private final StringRedisTemplate stringRedisTemplate;

    private final BucketService bucketService;

    public FilesServiceImpl(StringRedisTemplate stringRedisTemplate
                            , BucketService bucketService) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.bucketService = bucketService;
    }

    @Override
    public boolean removeFilesById(String id, String bid) {
        Files f = getById(id);
        // 删减bucket占用容量
        bucketService.updateBucketSizeWithFile(f.getBid(),f.getFileSize() * -1);
        String md5 = f.getMd5();
        long existFileNum = query().eq("md5", md5).count();
        if(existFileNum == 1){
            // 没有别的桶存在该文件
            String path = filePath + f.getPath();
            if(FileUtil.file(path).exists()){
                FileUtil.del(path);
            }
            // 删除md5列表
            stringRedisTemplate.opsForSet().remove(RedisConstant.FILE_MD5_LIST,f.getMd5());
        }
        LambdaQueryWrapper<Files> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Files::getId,id);
        queryWrapper.eq(Files::getBid,bid);
        return remove(queryWrapper);
    }


}




