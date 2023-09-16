package com.hzj.Mapper;

import com.hzj.pojo.Blog;
import com.hzj.util.PageQueryUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: tbBlogMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  15:50
 * @edition 1.0
 */
@Mapper
public interface BlogMapper {
    //通过工具类进行分页查询
    List<Blog> selectListBlog(PageQueryUtil pageQueryUtil);

    //获取具体的博客条数
    int getTotalBlogs(PageQueryUtil pageQueryUtil);

    //添加博客数据到数据库
    int insertSelective(Blog blog);

    Blog getBlogById(Integer blogId);

    //修改博客
    int updateBlog(Blog blog);

    //查询所有Blog数据
    Blog selectAllBlog(Blog blog);

    //博客的删除方法，但是他不是真的删除，是修改了is_deleted字段为1页面不在查询0
    int deleteBatch(Integer[] ids);

    /**
     * 分页查询首页，显示的博客数据
     *
     * @param type
     * @param limit
     * @return
     */
    List<Blog> findBlogListByType(@Param("type") int type, @Param("limit") int limit);

    List<Blog> getBlogsPageByTagId(PageQueryUtil pageUtil);

    /**因为xml表中进行了条件控制所以本方法实现两个功能
     * 修改分类表之后需要修改博客表中的分类数据
     *因为分类数据被删除所以博客的数据也要删除
     * @param categoryName
     * @param categoryId
     * @param ids
     * @return
     */
    int updateBlogCategory(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids") Integer[] ids);


    Blog selectBySubUrl(String subUrl);

    void updateByPrimaryKey(Blog blog);
}