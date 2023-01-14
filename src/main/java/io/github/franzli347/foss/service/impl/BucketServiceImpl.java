package io.github.franzli347.foss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.entity.Bucket;
import io.github.franzli347.foss.entity.BucketPrivilege;
import io.github.franzli347.foss.exception.BucketException;
import io.github.franzli347.foss.mapper.BucketMapper;
import io.github.franzli347.foss.service.BucketPrivilegeService;
import io.github.franzli347.foss.service.BucketService;
import io.github.franzli347.foss.service.FilesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author FranzLi
* @description 针对表【tb_bucket】的数据库操作Service实现
* @createDate 2022-12-15 10:46:45
*/
@Service
public class BucketServiceImpl extends ServiceImpl<BucketMapper, Bucket>
    implements BucketService{

    private final FilesService filesService;
    private final BucketPrivilegeService privilegeService;
    public BucketServiceImpl(FilesService filesService, BucketPrivilegeService privilegeService) {
        this.filesService = filesService;
        this.privilegeService = privilegeService;
    }

    @Override
    public List<Bucket> getBucketsByUserIdWithPage(int userId, int page, int size) {
      return baseMapper.getBucketsByUserIdWithPage(userId,page,size);
    }

    @Override
    public List<Bucket> listAll(int userId, int page, int size) {
        return baseMapper.listAll(userId,page,size);
    }

    @Override
    public boolean updateBucketSize(Integer bid, double fileSize) {
        return baseMapper.updateBucketSize(bid,fileSize);
    }

    @Override
    public boolean removeBucket(int id) {
        long bid = filesService.query().eq("bid", id).count();
        if(bid > 0){
            throw new BucketException("bucket_is_not_empty");
        }
        privilegeService.remove(new QueryWrapper<BucketPrivilege>().eq("bid",id));
        return removeById(id);
    }
}




