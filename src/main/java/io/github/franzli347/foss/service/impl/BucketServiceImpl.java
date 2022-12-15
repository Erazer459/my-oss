package io.github.franzli347.foss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.foss.entity.Bucket;
import io.github.franzli347.foss.service.BucketService;
import io.github.franzli347.foss.mapper.BucketMapper;
import org.springframework.stereotype.Service;

/**
* @author FranzLi
* @description 针对表【tb_bucket】的数据库操作Service实现
* @createDate 2022-12-15 10:46:45
*/
@Service
public class BucketServiceImpl extends ServiceImpl<BucketMapper, Bucket>
    implements BucketService{

}




