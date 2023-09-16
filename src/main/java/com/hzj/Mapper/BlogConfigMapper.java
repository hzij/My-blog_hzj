package com.hzj.Mapper;

import com.hzj.pojo.BlogConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: BlogConfigMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  12:31
 * @edition 1.0
 */
@Mapper
public interface BlogConfigMapper {
    List<BlogConfig> getConfigAll();

    BlogConfig selectByPrimaryKey(String configName);

    int updateByPrimaryKeySelective(BlogConfig record);

}
