package io.github.franzli347.foss.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.model.vo.BucketWithPriviledge;
import io.github.franzli347.foss.model.entity.Bucket;
import io.github.franzli347.foss.model.entity.BucketPrivilege;
import io.github.franzli347.foss.exception.BucketException;
import io.github.franzli347.foss.web.mapper.BucketMapper;
import io.github.franzli347.foss.web.service.BucketPrivilegeService;
import io.github.franzli347.foss.web.service.BucketService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author FranzLi
 * @description 针对表【tb_bucket】的数据库操作Service实现
 * @createDate 2022-12-15 10:46:45
 */
@Service
public class BucketServiceImpl extends ServiceImpl<BucketMapper, Bucket>
        implements BucketService {
    private final double EPSILON = 0.001;
    private final BucketPrivilegeService privilegeService;

    public BucketServiceImpl(BucketPrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @Override
    public List<Bucket> getBucketsByUserIdWithPage(int userId, int page, int size) {
        return baseMapper.getBucketsByUserIdWithPage(userId, page, size);
    }

    @Override
    public IPage<BucketWithPriviledge> listAll(int userId, int page, int size) {
        IPage<BucketPrivilege> p = new Page<>(page, size);
        LambdaQueryWrapper<BucketPrivilege> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BucketPrivilege::getUid, userId);
        privilegeService.page(p, wrapper);
        List<BucketPrivilege> records = p.getRecords();
        List<Bucket> buckets = listByIds(records.stream().map(BucketPrivilege::getBid).toList());
        List<BucketWithPriviledge> bwp = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            for (int j = 0; j < buckets.size(); j++) {
                BucketPrivilege bucketPrivilege = records.get(i);
                Bucket bucket = buckets.get(j);
                if (bucketPrivilege.getBid() == bucket.getId()) {
                    bwp.add(new BucketWithPriviledge(bucket.getId(), bucket.getName()
                            , bucket.getTotalSize(), bucket.getUid()
                            , bucket.getUsedSize(), bucket.getCreateTime(), bucketPrivilege.getPrivilege()));
                }
            }
        }
        IPage<BucketWithPriviledge> res = new Page<>(page, size);
        res.setRecords(bwp);
        res.setCurrent(p.getCurrent());
        res.setSize(p.getSize());
        res.setTotal(p.getTotal());
        res.setPages(p.getPages());
        return res;
    }

    @Override
    public boolean updateBucketSizeWithFile(Integer bid, double fileSize) {
        return baseMapper.updateBucketSize(bid, fileSize);
    }

    @Override
    public boolean removeBucket(int id) {
        Bucket byId = getById(id);
        if (Math.abs(byId.getUsedSize()) > EPSILON) {
            throw new BucketException("bucket_is_not_empty");
        }
        //删除相关权限
        privilegeService.remove(new LambdaQueryWrapper<BucketPrivilege>().eq(BucketPrivilege::getBid, id));
        return removeById(id);
    }

    @Override
    public boolean updateBucketData(Bucket bucket) {
        Integer id = bucket.getId();
        if (Optional.ofNullable(bucket.getTotalSize()).isPresent()) {
            Bucket byId = getById(id);
            if (byId.getUsedSize() > bucket.getTotalSize()) {
                throw new BucketException("当前存储文件容量大于目标容量，删除文件后再试。");
            }
        }
        return updateById(bucket);
    }

    @Override
    public IPage<Bucket> getLoginUserBucket(int userId, int page, int size) {
        IPage<Bucket> p = new Page<>(page, size);
        LambdaQueryWrapper<Bucket> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bucket::getUid, userId);
        return page(p, queryWrapper);
    }
}




