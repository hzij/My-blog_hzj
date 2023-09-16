package com.hzj.service.Impl;

import com.hzj.Mapper.LinkMapper;
import com.hzj.pojo.Link;
import com.hzj.service.LinkService;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PackageName :com.hzj.service.Impl
 * ClassName: LinkServiceImpl
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/13  16:14
 * @edition 1.0
 */
@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkMapper linkMapper;
    @Override
    public PageResult linkPage(PageQueryUtil pageQueryUtil) {
        List<Link> links = linkMapper.selectLinkList(pageQueryUtil);
        int total= linkMapper.selectCount();
        PageResult pageResult = new PageResult(links, total, pageQueryUtil.getPage(), pageQueryUtil.getLimit());
        return pageResult;
    }

    @Override
    public Boolean updateLink(Link link) {
        Link linkId = linkMapper.selectLinkById(link);
        if (linkId == null) {
            return false;
        } else {
            linkId.setLinkName(link.getLinkName());
            linkId.setLinkType(link.getLinkType());
            linkId.setLinkRank(link.getLinkRank());
            linkId.setLinkUrl(link.getLinkUrl());
            linkId.setLinkDescription(link.getLinkDescription());
            int i = linkMapper.updateLink(linkId);
            return i > 0;
        }
    }

    @Override
    public Link selectLinkById(Integer linkId) {
        Link link = new Link();
        link.setLinkId(linkId);
        return linkMapper.selectLinkById(link);
    }

    @Override
    public Boolean saveLink(Link link) {
     return linkMapper.insertLink(link)>0;
    }
    @Override
    public Boolean deletedLink(Integer[] ids){
        int i = linkMapper.deleteLink(ids);
        return i>0;
    }
}
