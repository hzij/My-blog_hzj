package com.hzj.service.Impl;

import com.hzj.Mapper.BlogCategoryMapper;
import com.hzj.Mapper.BlogMapper;
import com.hzj.pojo.Blog;
import com.hzj.pojo.BlogCategory;
import com.hzj.service.BlogCategoryService;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import com.hzj.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * PackageName :com.hzj.service.Impl
 * ClassName: BlogCategoryServiceImpl
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  22:26
 * @edition 1.0
 */
@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {
    @Autowired
    private BlogCategoryMapper blogCategoryMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Override
    public List<BlogCategory> getAllCategories() {
        return blogCategoryMapper.findCategoryList(null);
    }

    @Override
    public Boolean insertCategorySer(String categoryName, String categoryIcon) {
        BlogCategory blogCategory = blogCategoryMapper.selectByName(categoryName);
        if (blogCategory==null){
            //没有这个分类可以添加,封装数据
            BlogCategory blogCategoryInsert= new BlogCategory();
            blogCategoryInsert.setCategoryName(categoryName);
            blogCategoryInsert.setCategoryIcon(categoryIcon);
            int category = blogCategoryMapper.insertCategory(blogCategoryInsert);
            return category>0;
        }
        return false;
    }

    @Override
    public PageResult ListBlogCategory(PageQueryUtil pageQueryUtil) {
        //查询到所有数据
        List<BlogCategory> categoryList = blogCategoryMapper.findCategoryList(pageQueryUtil);
        //获得具体有几条数据
        int categoryCount = blogCategoryMapper.selectCountCategory();
        PageResult pageResult = new PageResult(categoryList, categoryCount, pageQueryUtil.getPage(), pageQueryUtil.getLimit());
        return pageResult;
    }

    @Override
    @Transactional
    public Boolean updateCategorySer(Integer categoryId, String categoryName, String categoryIcon) {
        BlogCategory blogCategory = blogCategoryMapper.selectByCategoryId(categoryId);
        if (blogCategory != null) {
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            blogMapper.updateBlogCategory(categoryName, blogCategory.getCategoryId(), new Integer[]{categoryId});
            int i = blogCategoryMapper.updateByPrimaryKeySelective(blogCategory);
            return i > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteCategorySer(Integer[] ids) {
        if (ids.length<1){
            return false;
        }
        //修改博客表中的分类数据
        blogMapper.updateBlogCategory("默认分类",0,ids);
        //删除分类数据
        int category = blogCategoryMapper.deleteCategory(ids);
        return category>0;
    }
}
