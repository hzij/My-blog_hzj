package com.hzj.pojo;

import lombok.Data;

/**
 * PackageName :com.hzj.pojo
 * ClassName: BlogConfig
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  12:28
 * @edition 1.0
 */
@Data
public class BlogConfig {
    private String configName;
    private String configValue;
    private String createTime;
    private String updateTime;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", configName=").append(configName);
        sb.append(", configValue=").append(configValue);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}
