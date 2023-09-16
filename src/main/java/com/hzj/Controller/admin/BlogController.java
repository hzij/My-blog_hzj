package com.hzj.Controller.admin;

import cn.hutool.core.util.ObjectUtil;
import com.hzj.pojo.Blog;
import com.hzj.service.BlogCategoryService;
import com.hzj.service.BlogService;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import com.hzj.util.Result;
import com.hzj.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * PackageName :com.hzj.Controller.admin
 * ClassName: BlogController
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  19:45
 * @edition 1.0
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class BlogController {
    @Resource
    private BlogCategoryService blogCategoryService;
    @Resource
    private BlogService blogService;

    /**
     * 页面渲染
     * @param request
     * @return
     */
    @GetMapping("/blogs")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "blogs");
        return "admin/blog";
    }

    /**
     * 博客页面的分页显示
     * @param params
     * @return
     */
    @GetMapping("/blogs/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(blogService.ListBlogPage(pageUtil));
    }
    @GetMapping("/blogs/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        request.setAttribute("categories", blogCategoryService.getAllCategories());
        return "admin/edit";
    }

    /**
     * 这是博客的发表，也是添加博客
     * @param blogCategoryId
     * @param blogContent
     * @param blogCoverImage
     * @param blogStatus
     * @param blogSubUrl
     * @param blogTags
     * @param blogTitle
     * @param enableComment
     * @return
     */
    @PostMapping("/blogs/save")
    @ResponseBody
    public Result  save(@RequestParam("blogCategoryId") Integer blogCategoryId,
                        @RequestParam("blogContent") String  blogContent,
                        @RequestParam("blogCoverImage") String blogCoverImage,
                        @RequestParam("blogStatus") Integer blogStatus,
                        @RequestParam(name = "blogSubUrl" ,required = false) String blogSubUrl,
                        @RequestParam("blogTags")   String blogTags,
                        @RequestParam("blogTitle")  String blogTitle,
                        @RequestParam("enableComment")Integer enableComment){
    //从前端获取数据之后，添加数据到库操作tb_blog表
        if (!StringUtils.hasText(blogTitle)){
            return ResultGenerator.genFailResult("博客名称不能为空");
        }
        if (!StringUtils.hasText(blogContent)){
            return ResultGenerator.genFailResult("博客内容不能为空");
        }
        if (!StringUtils.hasText(blogCoverImage)){
            return ResultGenerator.genFailResult("博客背景不能为空");
        }
        if (blogTitle.trim().length() > 150) {
            return ResultGenerator.genFailResult("标题过长");
        }
        if (!StringUtils.hasText(blogTags)) {
            return ResultGenerator.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return ResultGenerator.genFailResult("标签过长");
        }
        if (blogSubUrl.trim().length() > 150) {
            return ResultGenerator.genFailResult("路径过长");
        }
        if (blogContent.trim().length() > 100000){
            return ResultGenerator.genFailResult("博客内容过长");
        }
        //封装数据
        Blog blog = new Blog();
        blog.setBlogContent(blogContent);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogTitle(blogTitle);
        blog.setBlogTags(blogTags);
        blog.setEnableComment(enableComment);
        String saveBlogResult = blogService.insertBlog(blog);
        if ("success".equals(saveBlogResult)){
            return  ResultGenerator.genSuccessResult("博客添加成功");
        }else {
            return  ResultGenerator.genFailResult(saveBlogResult);
        }
    }

    /**
     * 这是请求跳转到修改页面，将数据回显用于修改，重新查询了分类回显到下拉框
     * @param request
     * @param blogId
     * @return
     */
    @GetMapping("/blogs/edit/{blogId}")
    public String updateSave(HttpServletRequest request, @PathVariable("blogId") Integer blogId){
        request.setAttribute("path","edit");
        //根据id查询获取的信息然后回显
        Blog blog = blogService.getByIdBlog(blogId);
        if (blog == null){
            //如果blog是空的话就返回错误页面
            return  "error/error_400";
        }
        request.setAttribute("blog", blog);
        //还要获得一次分类信息
        request.setAttribute("categories",blogCategoryService.getAllCategories());
        return "admin/edit";
    }

    @PostMapping("/blogs/update")
    @ResponseBody
    public Result updateBlogs(@RequestParam("blogCategoryId") Integer blogCategoryId,
                              @RequestParam("blogContent") String  blogContent,
                              @RequestParam("blogCoverImage") String blogCoverImage,
                              @RequestParam("blogStatus") Integer blogStatus,
                              @RequestParam(name = "blogSubUrl" ,required = false) String blogSubUrl,
                              @RequestParam("blogTags")   String blogTags,
                              @RequestParam("blogTitle")  String blogTitle,
                              @RequestParam("enableComment")Integer enableComment,
                              @RequestParam("blogId") Integer blogId){
        if (!StringUtils.hasText(blogTitle)){
            return ResultGenerator.genFailResult("博客名称不能为空");
        }
        if (!StringUtils.hasText(blogContent)){
            return ResultGenerator.genFailResult("博客内容不能为空");
        }
        if (!StringUtils.hasText(blogCoverImage)){
            return ResultGenerator.genFailResult("博客背景不能为空");
        }
        if (blogTitle.trim().length() > 150) {
            return ResultGenerator.genFailResult("标题过长");
        }
        if (!StringUtils.hasText(blogTags)) {
            return ResultGenerator.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return ResultGenerator.genFailResult("标签过长");
        }
        if (blogSubUrl.trim().length() > 150) {
            return ResultGenerator.genFailResult("路径过长");
        }
        if (blogContent.trim().length() > 100000){
            return ResultGenerator.genFailResult("博客内容过长");
        }
        //封装数据
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setBlogContent(blogContent);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogTitle(blogTitle);
        blog.setBlogTags(blogTags);
        blog.setEnableComment(enableComment);
        String s = blogService.updateBlog(blog);
        if ("success".equals(s)){
            return ResultGenerator.genSuccessResult("修改成功");
        }else {
            return ResultGenerator.genSuccessResult("修改失败");
        }
    }

    /**
     * 是修改is_deleted实现页面不显示
     * @return
     */
    @PostMapping("/blogs/delete")
    @ResponseBody
    public Result deleteBlog(@RequestBody Integer[] ids){
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (!blogService.deleteBatch(ids)){
            return ResultGenerator.genFailResult("删除失败");
        }else {
            return ResultGenerator.genSuccessResult();
        }
    }




}
