package com.hzj.Mapper;

import com.hzj.pojo.Blog;
import com.hzj.pojo.BlogCategory;
import com.hzj.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: BlogCategoryMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  21:52
 * @edition 1.0
 */
@Mapper
public interface BlogCategoryMapper {
    //博客管理分页的查询操作
    List<BlogCategory> findCategoryList(PageQueryUtil pageUtil);

    //博客分类使用的次数越多，category_rank数就越大
    Boolean  updateRank(Integer BlogCategoryId,Integer CategoryRank);

    //根据分类id查询是否有这个名称
    BlogCategory selectByCategoryId(Integer blogCategoryId);
    //进行博客分类的修改
    int updateByPrimaryKeySelective(BlogCategory blogCategory);
    //通过id查询分类数据
    List<BlogCategory>   selectByCategoryIds(List<Integer> categoryIds);
    //通过名称查询是否有这个分类
    BlogCategory  selectByName(String categoryName);
    //查询分类表中有多少数据
    int selectCountCategory();
    //插入数据
    int insertCategory(BlogCategory blogCategory);
    /**
     * 通过id修改is_deleted字段进行删除，实际是不显示，数据库并没有删除
     * @param ids
     * @return
     */
    int   deleteCategory(Integer[] ids);


    Integer getTotalBlogComments(Map params);

    Integer getCommentTotal();
}
