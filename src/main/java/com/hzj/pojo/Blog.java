package com.hzj.pojo;

/**
 * PackageName :com.hzj.pojo
 * ClassName: tbBlog
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  15:40
 * @edition 1.0
 */

import lombok.Data;

/**
 * blog_idbigint NOT NULL//博客表主键id
 * blog_titlevarchar(200) NOT NULL//博客标题
 * blog_sub_urlvarchar(200) NOT NULL//博客自定义路径url
 * blog_cover_imagevarchar(200) NOT NULL//博客封面图
 * blog_contentmediumtext NOT NULL//博客内容
 * blog_category_idint NOT NULL//博客分类id
 * blog_category_namevarchar(50) NOT NULL//博客分类(冗余字段)
 * blog_tagsvarchar(200) NOT NULL//博客标签
 * blog_statustinyint NOT NUL//L0-草稿 1-发布
 * blog_viewsbigint NOT NULL//阅读量
 * enable_commenttinyint NOT NULL//0-允许评论 1-不允许评论
 * is_deletedtinyint NOT NULL//是否删除 0=否 1=是
 * create_timedatetime NOT NULL//添加时间
 * update_timedatetime NULL//修改时间
 */
@Data
public class Blog {
    private Integer blogId;//博客表主键id
    private String blogTitle;//博客标题
    private String blogSubUrl;//博客自定义路径url
    private String blogCoverImage;//博客封面图
    private String blogContent;//博客内容
    private Integer blogCategoryId;//博客分类id
    private String blogCategoryName;//博客分类(冗余字段)
    private String blogTags;//博客标签
    private Integer blogStatus;//0-草稿 1-发布
    private  Long blogViews;//阅读量
    private Integer enableComment;//0-允许评论 1-不允许评论
    private Integer isDeleted;//是否删除 0=否 1=是
    private String createTime;//添加时间
    private String updateTime;//修改时间
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", blogId=").append(blogId);
        sb.append(", blogTitle=").append(blogTitle);
        sb.append(", blogSubUrl=").append(blogSubUrl);
        sb.append(", blogCoverImage=").append(blogCoverImage);
        sb.append(", blogCategoryId=").append(blogCategoryId);
        sb.append(", blogCategoryName=").append(blogCategoryName);
        sb.append(", blogTags=").append(blogTags);
        sb.append(", blogStatus=").append(blogStatus);
        sb.append(", blogViews=").append(blogViews);
        sb.append(", enableComment=").append(enableComment);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", blogContent=").append(blogContent);
        sb.append("]");
        return sb.toString();
    }
}
