package com.hzj.service;

import com.hzj.pojo.Blog;
import com.hzj.pojo.vo.BlogDetailVO;
import com.hzj.pojo.vo.SimpleBlogListVO;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import com.hzj.util.Result;

import java.util.List;

/**
 * PackageName :com.hzj.service
 * ClassName: BlogService
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  20:13
 * @edition 1.0
 */
public interface BlogService {
    //通过工具类进行分页查询
    PageResult ListBlogPage(PageQueryUtil pageQueryUtil);

    //进行博客内容数据库的添加
    String insertBlog(Blog blog);

    //通过博客id查询对应的信息，回显数据
    Blog getByIdBlog(Integer blogId);

    //根据博客id修改博客
    String updateBlog(Blog blogId);

    Boolean   deleteBatch(Integer[] ids);

    PageResult getBlogsForIndexPage(int page);
    List<SimpleBlogListVO>  getBlogListForIndexPage(int type);

    PageResult getBlogsPageByCategory(String categoryName, int page);

    BlogDetailVO getBlogDetail(Integer id);

    PageResult getBlogsPageByTag(String tagName, int page);


    PageResult getBlogsPageBySearch(String keyword, int page);

    BlogDetailVO getBlogDetailBySubUrl(String subUrl);
}