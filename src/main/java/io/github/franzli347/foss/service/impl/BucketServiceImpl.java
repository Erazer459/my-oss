package io.github.franzli347.foss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.entity.Bucket;
import io.github.franzli347.foss.entity.BucketPrivilege;
import io.github.franzli347.foss.exception.BucketException;
import io.github.franzli347.foss.mapper.BucketMapper;
import io.github.franzli347.foss.service.BucketPrivilegeService;
import io.github.franzli347.foss.service.BucketService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
* @author FranzLi
* @description 针对表【tb_bucket】的数据库操作Service实现
* @createDate 2022-12-15 10:46:45
*/
@Service
public class BucketServiceImpl extends ServiceImpl<BucketMapper, Bucket>
    implements BucketService{
    private final double EPSILON = 0.001;
    private final BucketPrivilegeService privilegeService;

    public BucketServiceImpl(BucketPrivilegeService privilegeService) {
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
    public boolean updateBucketSizeWithFile(Integer bid, double fileSize) {
        return baseMapper.updateBucketSize(bid,fileSize);
    }

    @Override
    public boolean removeBucket(int id) {
        Bucket byId = getById(id);
        if(Math.abs(byId.getUsedSize()) > EPSILON){
            throw new BucketException("bucket_is_not_empty");
        }
        privilegeService.remove(new LambdaQueryWrapper<BucketPrivilege>().eq(BucketPrivilege::getBid,id));//删除相关权限
        return removeById(id);
    }

    @Override
    public boolean updateBucketData(Bucket bucket) {
        Integer id = bucket.getId();
        if (Optional.ofNullable(bucket.getTotalSize()).isPresent()) {
            Bucket byId = getById(id);
            if(byId.getUsedSize() > bucket.getTotalSize()){
                throw new BucketException("当前存储文件容量大于目标容量，删除文件后再试。");
            }
        }
        return updateById(bucket);
    }
}




