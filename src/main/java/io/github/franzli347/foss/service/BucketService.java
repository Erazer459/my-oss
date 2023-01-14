package io.github.franzli347.foss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.foss.entity.Bucket;

import java.util.List;
import java.util.Map;

/**
* @author FranzLi
* @description 针对表【tb_bucket】的数据库操作Service
* @createDate 2022-12-15 10:46:45
*/
public interface BucketService extends IService<Bucket> {

    List<Bucket> getBucketsByUserIdWithPage(int userId,int page,int size);

    List<Bucket> listAll(int userId, int page, int size);

    boolean updateBucketSize(Integer bid, double fileSize);

    boolean removeBucket(int id);
}
