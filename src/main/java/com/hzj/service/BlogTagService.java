package com.hzj.service;

import com.hzj.pojo.Blog;
import com.hzj.pojo.BlogTag;
import com.hzj.pojo.BlogTagCount;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;

import java.util.List;

/**
 * PackageName :com.hzj.service
 * ClassName: BlogTagService
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  13:44
 * @edition 1.0
 */
public interface BlogTagService {

    public List<BlogTagCount> getBlogTagCountForIndex();

    /**
     * 分页显示标签数据
     * @param pageQueryUtil
     * @return
     */
    PageResult ListTagSer(PageQueryUtil pageQueryUtil);
    /**
     * 添加标签数据
     */
    Boolean insertTag(String tagName);

    Boolean deletedTag(Integer[] ids);

}
