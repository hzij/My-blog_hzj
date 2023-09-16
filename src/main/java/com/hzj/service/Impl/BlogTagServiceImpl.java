package com.hzj.service.Impl;

import com.hzj.Mapper.BlogTagCountMapper;
import com.hzj.Mapper.BlogTagMapper;
import com.hzj.Mapper.BlogTagRelationMapper;
import com.hzj.pojo.BlogTag;
import com.hzj.pojo.BlogTagCount;
import com.hzj.service.BlogTagService;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * PackageName :com.hzj.service.Impl
 * ClassName: BlogTagServiceImpl
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  13:46
 * @edition 1.0
 */
@Service
public class BlogTagServiceImpl implements BlogTagService {
    @Autowired
    private BlogTagCountMapper blogTagCountMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        return blogTagCountMapper.getTotalTags();
    }

    @Override
    public Boolean deletedTag(Integer[] ids) {
        List<BlogTag> blogTags = blogTagRelationMapper.selectAllRelationById(ids);
        //并不删除其他表中的数据
        if (!CollectionUtils.isEmpty(blogTags)){
            return false;
        }
        int i = blogTagMapper.deleteTag(ids);
        return i>0;
    }

    @Override
    public Boolean insertTag(String tagName) {
        BlogTag blogTag = blogTagMapper.selectByNameTag(tagName);
        if (blogTag!=null){
         return false;
        }else {
            int i = blogTagMapper.insertTag(tagName);
            return i>0;
        }
    }

    @Override
    public PageResult ListTagSer(PageQueryUtil pageQueryUtil) {
        List<BlogTag> blogTags = blogTagMapper.selectAllTag(pageQueryUtil);
        int total = blogTagMapper.getAllCountTag();
        PageResult pageResult=new PageResult(blogTags,total,pageQueryUtil.getPage(),pageQueryUtil.getLimit());
        return pageResult;
    }
}
