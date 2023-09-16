package com.hzj.service.Impl;

import com.hzj.Mapper.BlogCommentMapper;
import com.hzj.pojo.Comment;
import com.hzj.service.BlogCommentService;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * PackageName :com.hzj.service.Impl
 * ClassName: BlogCommentServiceImpl
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  10:44
 * @edition 1.0
 */
@Service
public class BlogCommentServiceImpl implements BlogCommentService {
    @Autowired
    private BlogCommentMapper blogCommentMapper;
    @Override
    public PageResult CommentListPage(PageQueryUtil pageQueryUtil) {
        //查询所有的评论
        List<Comment> comments = blogCommentMapper.SelectListComment(pageQueryUtil);
        //获得具体的评论数目；
        int total = blogCommentMapper.getCommentTotal(pageQueryUtil);
        PageResult pageResult = new PageResult(comments, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        //将数据返回给Controller
        return pageResult;
    }

    @Override
    public Boolean updateToExamine(Integer[] ids) {
        return blogCommentMapper.updateToExamine(ids)>0;
    }

    @Override
    public Boolean updateComment(Integer commentId, String replyBody) {
        Comment comment = blogCommentMapper.selectCommentById(commentId);
        //comment不等于空且以及审核就可以评论了
        if (comment!=null && comment.getCommentStatus() ==1){
            comment.setReplyBody(replyBody);
            comment.setReplyCreateTime(String.valueOf(new Date()));
           return blogCommentMapper.updateComment(commentId,replyBody)>0;
        }
        return false;
    }

    @Override
    public Boolean deleteComment(Integer[] ids) {
        return blogCommentMapper.deleteComment(ids)>0;
    }
}
