package com.hzj.Controller.admin;

import com.hzj.service.BlogTagService;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import com.hzj.util.Result;
import com.hzj.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * PackageName :com.hzj.Controller.admin
 * ClassName: BlogTagContraller
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/13  13:38
 * @edition 1.0
 */
@Controller
@RequestMapping("/admin")
public class BlogTagController {
    @Resource
    private BlogTagService blogTagService;
    @GetMapping("/tags")
    public String toTag(){
        return "/admin/tag";
    }
    @GetMapping("/tags/list")
    @ResponseBody
    public Result ListTag(@RequestParam Map<String,Object> params){
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty("limit")) {
            return ResultGenerator.genFailResult("参数错误");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        PageResult pageResult = blogTagService.ListTagSer(pageQueryUtil);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @PostMapping("/tags/save")
    @ResponseBody
    public Result saveTag(@RequestParam String tagName){
        if (tagName==null){
            return ResultGenerator.genFailResult("参数错误");
        }
        if (blogTagService.insertTag(tagName)){
            return ResultGenerator.genSuccessResult("添加成功");
        }else {
            return ResultGenerator.genFailResult("添加失败");
        }
    }
    @PostMapping("tags/delete")
    @ResponseBody
    public Result deleteTag(@RequestBody Integer[] ids){
        //并不删除其他表中的标签信息只对标签表进行修改
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数错误");
        }
        Boolean aBoolean = blogTagService.deletedTag(ids);
        if (aBoolean){
            return ResultGenerator.genSuccessResult("删除成功");
        }else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
