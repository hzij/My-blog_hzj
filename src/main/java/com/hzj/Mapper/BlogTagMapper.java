package com.hzj.Mapper;

import com.hzj.pojo.Blog;
import com.hzj.pojo.BlogTag;
import com.hzj.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: BlogTagMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/10  14:46
 * @edition 1.0
 */
@Mapper
public interface BlogTagMapper {
    //查询标签是否存在
    BlogTag selectByNameTag(String blogTags);
    //标签的添加，只需要添加标签的名字，其他数据前端回显主键自增
    int batchInsertBlogTag(List<BlogTag> tagList);
    //查询所有标签
    List<BlogTag> selectAllTag(PageQueryUtil pageQueryUtil);
    //获取表中有多少数据
    int getAllCountTag();

    int insertTag(String tagName);

    //通过修改is_deleted字段来实现视觉上的删除,通过在xml中控制实现批量删除和单独删除的实现
    int deleteTag(Integer[] ids);
    //根据id显示查询
}
