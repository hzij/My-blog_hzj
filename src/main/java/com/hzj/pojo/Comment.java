package com.hzj.pojo;

import lombok.Data;

/**
 * PackageName :com.hzj.pojo
 * ClassName: Comment
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  10:11
 * @edition 1.0
 */
@Data
public class Comment {
    private Integer commentId;
    private Integer blogId;
    private String commentator;
    private String email;
    private String websiteUrl;
    private String commentBody;
    private String commentCreateTime;
    private String commentatorIp;
    private String replyBody;
    private String replyCreateTime;
    private Integer commentStatus;
    private Integer isDeleted;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentId=").append(commentId);
        sb.append(", blogId=").append(blogId);
        sb.append(", commentator=").append(commentator);
        sb.append(", email=").append(email);
        sb.append(", websiteUrl=").append(websiteUrl);
        sb.append(", commentBody=").append(commentBody);
        sb.append(", commentCreateTime=").append(commentCreateTime);
        sb.append(", commentatorIp=").append(commentatorIp);
        sb.append(", replyBody=").append(replyBody);
        sb.append(", replyCreateTime=").append(replyCreateTime);
        sb.append(", commentStatus=").append(commentStatus);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }


}
