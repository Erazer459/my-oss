package io.github.franzli347.foss.web.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.common.constant.RedisConstant;
import io.github.franzli347.foss.exception.FileException;
import io.github.franzli347.foss.model.entity.Files;
import io.github.franzli347.foss.web.mapper.FilesMapper;
import io.github.franzli347.foss.web.service.BucketService;
import io.github.franzli347.foss.web.service.FilesService;
import io.github.franzli347.foss.model.vo.FilesVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author FranzLi
 * @description 针对表【tb_files】的数据库操作Service实现
 * @createDate 2022-12-15 10:46:45
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements FilesService {


    @Value("${pathMap.source}")
    private String filePath;

    private final StringRedisTemplate stringRedisTemplate;

    private final BucketService bucketService;

    public FilesServiceImpl(StringRedisTemplate stringRedisTemplate, BucketService bucketService) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.bucketService = bucketService;
    }

    @Override
    public boolean removeFilesById(String id, String bid) {
        Files f = Optional.ofNullable(getById(id)).orElseThrow(() -> new FileException("文件不存在!"));
        // 删减bucket占用容量
        try {
            bucketService.updateBucketSizeWithFile(f.getBid(), f.getFileSize() * -1);
            String md5 = f.getMd5();
            long existFileNum = query().eq("md5", md5).count();
            if (existFileNum == 1) {
                // 没有别的桶存在该文件
                String path = filePath + f.getPath();
                if (FileUtil.file(path).exists()) {
                    FileUtil.del(path);
                }
                // 删除md5列表
                stringRedisTemplate.opsForSet().remove(RedisConstant.FILE_MD5_LIST, f.getMd5());
            }
        }catch (Exception e){
               log.error("尝试删除一个不存在的文件 | 删除文件出错了!");
        }
        LambdaQueryWrapper<Files> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Files::getId, id);
        queryWrapper.eq(Files::getBid, bid);
        return remove(queryWrapper);
    }

    @Override
    public IPage<FilesVo> listAllInBucket(String bid, Integer page, Integer size) {
        IPage<Files> p = new Page<>(page, size);
        LambdaQueryWrapper<Files> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Files::getBid, bid);
        page(p, queryWrapper);
        var filesvos = p.getRecords().stream().map(v -> new FilesVo(v.getId(), v.getFileName(), v.getFileSize()
                , v.getCreateTime(), v.getBid(), v.getMd5(), v.getFileType())).toList();
        IPage<FilesVo> res = new Page<>();
        res.setPages(p.getPages());
        res.setTotal(p.getTotal());
        res.setSize(p.getSize());
        res.setCurrent(p.getCurrent());
        res.setRecords(filesvos);
        return res;
    }


}




