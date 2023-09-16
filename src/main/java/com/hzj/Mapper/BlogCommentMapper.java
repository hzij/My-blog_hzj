package com.hzj.Mapper;

import com.hzj.pojo.Comment;
import com.hzj.util.PageQueryUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: BlogCommentMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  10:24
 * @edition 1.0
 */
@Mapper
public interface BlogCommentMapper {
    /**
     * 查询所有的评论,在xml编写语句时要将模糊查询也通过if实现
     * @return
     */
    List<Comment> SelectListComment(PageQueryUtil pageQueryUtil);

    /**
     * 获得评论的总体数量
     * @param pageQueryUtil
     * @return
     */
    int  getCommentTotal(PageQueryUtil pageQueryUtil);

    /**
     * 修改评论comment_status审核列让状态改变
     * @return
     */
    int updateToExamine(Integer[] ids);

    /**
     * 删除评论
     * @param ids
     * @return
     */
    int deleteComment(Integer[] ids);

    /**
     * 根据id查询评论数据
     * @param commentId
     * @param replyBody
     * @return
     */
    Comment selectCommentById(Integer commentId);

    /**
     * 修改评论数据
     * @param commentId
     * @param replyBody
     * @return
     */


    int updateComment(Integer commentId,String replyBody);
}
