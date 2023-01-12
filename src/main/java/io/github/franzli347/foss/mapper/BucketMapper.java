package io.github.franzli347.foss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.franzli347.foss.entity.Bucket;

import java.util.List;

/**
* @author FranzLi
* @description 针对表【tb_bucket】的数据库操作Mapper
* @createDate 2022-12-15 10:46:45
* @Entity io.github.franzli347.foss.entity.Bucket
*/
public interface BucketMapper extends BaseMapper<Bucket> {

    List<Bucket> getBucketsByUserIdWithPage(int userId, int page, int size);

    List<Bucket> listAll(int userId, int page, int size);

    boolean updateBucketSize(Integer bid, double fileSize);
}




