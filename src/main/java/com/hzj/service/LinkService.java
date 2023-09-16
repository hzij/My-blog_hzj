package com.hzj.service;

import com.hzj.pojo.Link;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;

import java.util.List;
import java.util.Map;

/**
 * PackageName :com.hzj.service
 * ClassName: LinkService
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/13  16:14
 * @edition 1.0
 */
public interface LinkService {
    /**
     * 分页查询的实现
     * @param pageQueryUtil
     * @return
     */
    PageResult  linkPage(PageQueryUtil pageQueryUtil);

    /**
     * 添加友链信息
     * @param link
     * @return
     */
    Boolean saveLink(Link link);

    /**
     * 修改友链
     * @param link
     * @return
     */
    Boolean updateLink(Link link);

    Link selectLinkById(Integer linkId);

    Boolean deletedLink(Integer[] ids);

    Map<Byte, List<Link>> getLinksForLinkPage();
}
