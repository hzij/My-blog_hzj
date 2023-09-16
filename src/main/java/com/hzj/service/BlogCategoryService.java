package com.hzj.service;

import com.hzj.pojo.BlogCategory;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * PackageName :com.hzj.service
 * ClassName: BlogCategoryService
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  22:26
 * @edition 1.0
 */
public interface BlogCategoryService {
    /**
     * 获得所有的分类信息并且回显
     * @return
     */
    public List<BlogCategory> getAllCategories();
    /**
     * 分页查询的实现
     */
    PageResult ListBlogCategory(PageQueryUtil pageQueryUtil);

    /**
     * 插入分类数据
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    Boolean    insertCategorySer(String categoryName,String categoryIcon);

    /**
     * 修改分类数据
     * @param categoryId
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    Boolean  updateCategorySer(Integer categoryId, String categoryName, String categoryIcon);

    /**
     * 修改is_deleted字段进行删除
     * @param ids
     * @return
     */
    Boolean  deleteCategorySer(Integer[] ids);
}
