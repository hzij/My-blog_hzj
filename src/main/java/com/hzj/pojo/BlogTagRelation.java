package com.hzj.pojo;

import lombok.Data;

/**
 * PackageName :com.hzj.pojo
 * ClassName: BlogTagRelation
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/10  15:10
 * @edition 1.0
 */
@Data
public class BlogTagRelation {
    private Integer relationId;
    private Integer blogId;
    private Integer tagId;
    private String createTime;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", relationId=").append(relationId);
        sb.append(", blogId=").append(blogId);
        sb.append(", tagId=").append(tagId);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}
