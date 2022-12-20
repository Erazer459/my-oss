package io.github.franzli347.foss;

import io.github.franzli347.foss.entity.Bucket;
import io.github.franzli347.foss.service.BucketService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class BucketServiceTest {

    @Autowired
    private BucketService bucketService;

    @Test
    public void testGetBucketsByUserIdWithPage(){
        List<Bucket> bucketsByUserIdWithPage = bucketService.getBucketsByUserIdWithPage(1, 0, 2);
        log.info("bucketsByUserIdWithPage:{}",bucketsByUserIdWithPage);
    }

}
