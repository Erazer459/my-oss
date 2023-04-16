package io.github.franzli347.foss.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName BackupSnapshot
 * @Author AlanC
 * @Date 2023/4/15 16:22
 * @Description 备份快照
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("backupSnapshot")
public class BackupSnapshot implements Serializable {//备份任务执行快照，存储在mongodb
    @MongoId
    private long id;
    private Bucket bucketInfo;
    private List<BucketPrivilege> privilegeInfos;
    private List<Files> filesInfo;
    private long taskId;
    private Date executedTime;

}
