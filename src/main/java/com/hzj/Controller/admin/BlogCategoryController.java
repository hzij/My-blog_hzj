package com.hzj.Controller.admin;

import com.hzj.service.BlogCategoryService;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import com.hzj.util.Result;
import com.hzj.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * PackageName :com.hzj.Controller.admin
 * ClassName: BlogCategoryController
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  22:09
 * @edition 1.0
 */
@Controller
@RequestMapping("/admin")
public class BlogCategoryController {
    @Resource
    private BlogCategoryService blogCategoryService;

    @RequestMapping("/categories")
    public String categoryTo() {
        return "admin/category";
    }

    @GetMapping("/categories/list")
    @ResponseBody
    public Result categoryPage(@RequestParam Map<String, Object> params) {
        //使用工具类进行分页查询与页面的显示
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty("limit")) {
            return ResultGenerator.genFailResult("参数错误");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(blogCategoryService.ListBlogCategory(pageQueryUtil));
    }

    @PostMapping("/categories/save")
    @ResponseBody
    public Result CatregprySave(@RequestParam("categoryName") String categoryName,
                                @RequestParam("categoryIcon") String categoryIcon,
                                HttpServletResponse response) throws IOException {
        if (!StringUtils.hasText(categoryName)) {
            return ResultGenerator.genFailResult("请输入分类名称");
        }
        if (!StringUtils.hasText(categoryIcon)) {
            return ResultGenerator.genFailResult("请选择分类图片");
        }
        if (blogCategoryService.insertCategorySer(categoryName, categoryIcon)) {
            return ResultGenerator.genSuccessResult("添加分类成功");
        } else {
            return ResultGenerator.genFailResult("添加失败");
        }
    }

    @PostMapping("/categories/update")
    @ResponseBody
    public Result updateCategory(@RequestParam("categoryId") Integer categoryId,
                                 @RequestParam("categoryName") String categoryName,
                                 @RequestParam("categoryIcon") String categoryIcon) {

        if (!StringUtils.hasText(categoryName)) {
            return ResultGenerator.genFailResult("请输入要修改的信息");
        }
        if (!StringUtils.hasText(categoryIcon)) {
            return ResultGenerator.genFailResult("请输入要修改的图标");
        }
        if (blogCategoryService.updateCategorySer(categoryId, categoryName, categoryIcon)) {
            return ResultGenerator.genSuccessResult("修改成功");
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

    @PostMapping("categories/delete")
    @ResponseBody
    public Result deleteCategory(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (blogCategoryService.deleteCategorySer(ids)) {
            return ResultGenerator.genSuccessResult("删除成功");
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }

    }
}
