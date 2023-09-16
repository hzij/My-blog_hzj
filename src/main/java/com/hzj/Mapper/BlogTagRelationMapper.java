package com.hzj.Mapper;

import com.hzj.pojo.Blog;
import com.hzj.pojo.BlogCategory;
import com.hzj.pojo.BlogTag;
import com.hzj.pojo.BlogTagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: BlogTagRelationMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/10  15:12
 * @edition 1.0
 */
@Mapper
public interface BlogTagRelationMapper {
    //添加标签与博客的数据
    int   insertTagBlog(List<BlogTag> allTagsList);
    //博客与标签关系表的添加
    int batchInsert(@Param("relationList") List<BlogTagRelation> blogTagRelationList);

    int deleteBlogTagRelation(Integer blogId);

    //通过id查询关系表
    List<BlogTag> selectAllRelationById(Integer[] ids);
}
