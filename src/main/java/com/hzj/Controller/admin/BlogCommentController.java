package com.hzj.Controller.admin;

import com.hzj.pojo.Comment;
import com.hzj.service.BlogCommentService;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.Result;
import com.hzj.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * PackageName :com.hzj.Controller.admin
 * ClassName: BlogCommentController
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  10:17
 * @edition 1.0
 */
@Controller
@Slf4j
@RequestMapping("/admin")
public class BlogCommentController {
    @Resource
    private BlogCommentService blogCommentService;
    /**
     * 进行评论页面的跳转
     * @return
     */
    @GetMapping("/comments")
    public String commentJump(){
        return "admin/comment";
    }

    @GetMapping("/comments/list")
    @ResponseBody
    public Result  listComment(@RequestParam Map<String,Object> prams){
        if (ObjectUtils.isEmpty(prams.get("page")) || ObjectUtils.isEmpty("limit")) {
            return ResultGenerator.genFailResult("参数错误");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(prams);
        return ResultGenerator.genSuccessResult(blogCommentService.CommentListPage(pageQueryUtil));
    }

    @PostMapping("/comments/checkDone")
    @ResponseBody
    public Result  CommentModeration(@RequestBody Integer[] ids){
        if (ids.length<1){
         return   ResultGenerator.genFailResult("参数错误");
        }
        if (blogCommentService.updateToExamine(ids)){
          return   ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("审核失败");
        }
    }

    @PostMapping("comments/delete")
    @ResponseBody
    public Result deleteComments(@RequestBody Integer[] ids){
        if (ids.length<1 ){
            return  ResultGenerator.genFailResult("参数错误");
        }
        System.out.println(blogCommentService.deleteComment(ids));
        if (blogCommentService.deleteComment(ids)){

            log.info("这是");
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
    @PostMapping("comments/reply")
    @ResponseBody
    public  Result  CommentResponse(@RequestParam("commentId") Integer commentId,
                                    @RequestParam("replyBody") String replyBody){
        //进行参数校验
        if (commentId==null || commentId<1 || replyBody ==null){
            return ResultGenerator.genFailResult("参数异常");
        }
        //调用Service进行数据库的添加
        if (blogCommentService.updateComment(commentId,replyBody)){
            return ResultGenerator.genSuccessResult("评论添加成功");
        }else {
            return ResultGenerator.genFailResult("评论添加失败");
        }
    }

}
