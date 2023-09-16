package com.hzj.pojo;

import lombok.Data;

/**
 * PackageName :com.hzj.pojo
 * ClassName: Link
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/13  16:09
 * @edition 1.0
 */
@Data
public class Link {
    private  Integer linkId;
    private Integer linkType;
    private String  linkName;
    private String  linkUrl;
    private String  linkDescription;
    private Integer linkRank;
    private Integer isDeleted;
    private String createTime;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", linkId=").append(linkId);
        sb.append(", linkType=").append(linkType);
        sb.append(", linkName=").append(linkName);
        sb.append(", linkUrl=").append(linkUrl);
        sb.append(", linkDescription=").append(linkDescription);
        sb.append(", linkRank=").append(linkRank);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}
