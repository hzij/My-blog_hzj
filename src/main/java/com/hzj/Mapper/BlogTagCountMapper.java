package com.hzj.Mapper;

import com.hzj.pojo.BlogTagCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: BlogTagCountMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  13:46
 * @edition 1.0
 */
@Mapper
public interface BlogTagCountMapper {
    List<BlogTagCount> getTotalTags();
}
