package com.hzj.service;

import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;

import java.util.List;

/**
 * PackageName :com.hzj.service
 * ClassName: BlogCommentService
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  10:43
 * @edition 1.0
 */
public interface BlogCommentService {
    /**
     * 通过工具类进行分页操作，PageQueryUtil在创建实例的时候也时Map作为参数，其中还
     * 声明了page与limit属性接收数据
     * @param pageQueryUtil
     * @return
     */
    PageResult CommentListPage(PageQueryUtil pageQueryUtil);

    /**
     * 评论审核
     * @param ids
     * @return
     */
    Boolean updateToExamine(Integer[] ids);

    /**
     * 删除评论
     * @param ids
     * @return
     */
    Boolean deleteComment(Integer[] ids);

    /**
     * 进行数据库的添加，这个时添加回复评论的
     * @return
     */
    Boolean updateComment(Integer commentId,String replyBody);
}
