package com.hzj.service.Impl;

import com.hzj.Mapper.BlogCategoryMapper;
import com.hzj.Mapper.BlogMapper;
import com.hzj.Mapper.BlogTagMapper;
import com.hzj.Mapper.BlogTagRelationMapper;
import com.hzj.pojo.Blog;
import com.hzj.pojo.BlogCategory;
import com.hzj.pojo.BlogTag;
import com.hzj.pojo.BlogTagRelation;
import com.hzj.pojo.vo.BlogDetailVO;
import com.hzj.pojo.vo.BlogListVO;
import com.hzj.pojo.vo.SimpleBlogListVO;
import com.hzj.service.BlogService;
import com.hzj.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * PackageName :com.hzj.service.Impl
 * ClassName: BlogServiceImpl
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  20:13
 * @edition 1.0
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogCategoryMapper blogCategoryMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;
    @Autowired
    private BlogCategoryMapper blogCommentMapper;
    @Override
    public PageResult ListBlogPage(PageQueryUtil pageQueryUtil) {
        //查询所有博客
        List<Blog> blogs = blogMapper.selectListBlog(pageQueryUtil);//博客数据
        //获得具体的博客数目
        int total = blogMapper.getTotalBlogs(pageQueryUtil);
        //将数据返回给前端
        PageResult pageResult = new PageResult(blogs, total, pageQueryUtil.size(), pageQueryUtil.getPage());
        return pageResult;
    }

    /**
     * 通过id修改is_deleted字段页面查询不到但数据库里面并没有删除
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return blogMapper.deleteBatch(ids) >0;
    }

    @Override
    public PageResult getBlogsForIndexPage(int page) {
        Map params=new HashMap();
        params.put("page",page);
        //每页8条
        params.put("limit",8);
        //过滤发布状态下的数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        List<Blog> blogs = blogMapper.selectListBlog(pageUtil);
        List<BlogListVO> blogListVOS=getBlogListVOsByBlogs(blogs);
        int total = blogMapper.getTotalBlogs(pageUtil);
        PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;

    }
    @Override
    public List<SimpleBlogListVO>  getBlogListForIndexPage(int type){
        List<SimpleBlogListVO> simpleBlogListVOS=new ArrayList<>();
        List<Blog> blogListByType = blogMapper.findBlogListByType(type, 9);
        if (!CollectionUtils.isEmpty(blogListByType)) {
            for (Blog blog : blogListByType) {
                SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
                BeanUtils.copyProperties(blog, simpleBlogListVO);
                simpleBlogListVOS.add(simpleBlogListVO);
            }
        }
        return simpleBlogListVOS;
    }

    //修改博客
    @Override
    @Transactional
    public String updateBlog(Blog blog) {
        /** 修改博客的时候仍然需要进行数据的处理
         * 1.获取到当前数据进行回显，已经完成
         *      要确保数据的存在
         *      中间要进行分类信息的确认，以及博客分类名称与博客分类的set,原来分类的排序值需要减一，重新选择的分类加一
         * 2.进行数据的处理
         *  ①标签信息的封装
         *  ②标签信息的比对如果没有则需要添加这个标签
         *  ③标签与博客的关系表的修改
         *      1)先将原来的关系删除  根据博客id
         *      2)添加之后的关系   有方法batchInsert实现
         */

        //完成前置重新设置分类名称，分类排序加一
        Blog allBlog = blogMapper.selectAllBlog(blog);
        BlogCategory blogCategory = blogCategoryMapper.selectByCategoryId(blog.getBlogCategoryId());
        if (allBlog==null){
            return "数据不存在";
        }
        //Controller封装的前端的修改数据
        allBlog.setBlogTitle(blog.getBlogTitle());
        allBlog.setBlogSubUrl(blog.getBlogSubUrl());
        allBlog.setBlogContent(blog.getBlogContent());
        allBlog.setBlogCoverImage(blog.getBlogCoverImage());
        allBlog.setBlogStatus(blog.getBlogStatus());
        allBlog.setEnableComment(blog.getEnableComment());
        allBlog.setBlogTags(blog.getBlogTags());
        if (blogCategory == null){
            //没有这个分类
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        }else {
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            blog.setBlogCategoryId(blog.getBlogCategoryId());
            blogCategory.setCategoryRank(blogCategory.getCategoryRank()+1);
        }
        //使用数组存放标签
        String[] tags =blog.getBlogTags().split(",");
        if (tags.length>6){
            return "标签长度不能超过六";
        }

        //声明集合保存标签数据,tagListForInsert是要添加的标签数据
        List<BlogTag> tagListForInsert=new ArrayList<>();
        //保存数据，进行博客与标签关系表的增加
        List<BlogTag> allTagsList=new ArrayList<>();
        for (int i=0;i<tags.length;i++){
            BlogTag blogTag = blogTagMapper.selectByNameTag(tags[i]);
            if (blogTag==null){
                //这个标签不存在需要添加
                BlogTag tempTag = new BlogTag();
                tempTag.setTagName(tags[i]);
                tagListForInsert.add(tempTag);
            }else{
                allTagsList.add(blogTag);
            }
        }
        if (!CollectionUtils.isEmpty(tagListForInsert)){
            //标签数据集合存入数据就说明这个标签不存在需要添加到标签表中，原来的博客与标签的关系需要删除
            blogTagMapper.batchInsertBlogTag(tagListForInsert);
        }

        //封装博客与标签关系表数据
        List<BlogTagRelation> blogTagRelations=new ArrayList<>();
        allTagsList.addAll(tagListForInsert);
        for (BlogTag blogTag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(blogTag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }
        if(blogCategory != null){
            blogCategoryMapper.updateByPrimaryKeySelective(blogCategory);
        }
        //进行博客与标签表的删除操作
        blogTagRelationMapper.deleteBlogTagRelation(blog.getBlogId());
        blogTagRelationMapper.batchInsert(blogTagRelations);
        if (blogMapper.updateBlog(allBlog)>0){
            return "success";
        }
       return  "修改失败";
    }

    @Override
    @Transactional
    public String insertBlog(Blog blog) {
        //先判断是否有这个分类，无此分类的话就默认分类分类id就为0
        BlogCategory blogCategory = blogCategoryMapper.selectByCategoryId(blog.getBlogCategoryId());
        if (blogCategory==null){
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        }else {
            //设置博客分类名称
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            //设置分类名称的排序值
            blogCategory.setCategoryRank(blogCategory.getCategoryRank()+1);
        }
        //处理标签数据
        //这是接收到的标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length>6){
            return "标签的数量限制为6";
        }
        //保存文章，并且标签的名称也需要添加，
        //多个标签使用List集合保存，这是新增的标签需要添加的那个
        List<BlogTag> tagListForInsert = new ArrayList<>();
        //这是所有的list标签，用于建立关系数据
        List<BlogTag> allTagsList = new ArrayList<>();
        if (blogMapper.insertSelective(blog)>0){

           for (int i=0;i<tags.length;i++){
               //查询标签的存在，如果标签不存在则添加,数组是String类型的
               BlogTag blogTag = blogTagMapper.selectByNameTag(tags[i]);
               if (blogTag == null){
                   //这个标签不存在，需要添加
                   BlogTag tempTag = new BlogTag();
                   tempTag.setTagName(tags[i]);
                   tagListForInsert.add(tempTag);
               }else {
                   allTagsList.add(blogTag);
               }
           }
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
                //如果tagListForInsert不是空的就添加
                blogTagMapper.batchInsertBlogTag(tagListForInsert);
            }
            if (blogCategory!=null){
               blogCategoryMapper.updateByPrimaryKeySelective(blogCategory);
            }
        }
        //进行博客与标签关系表的数据保存，以及数据处理
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        allTagsList.addAll(tagListForInsert);//添加所有的标签信息
        for (BlogTag tag : allTagsList) {
            //将博客id与标签id封装
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            //将值添加到集合中
            blogTagRelations.add(blogTagRelation);
        }
        if (blogTagRelationMapper.batchInsert(blogTagRelations) > 0){
            return "success";
        }
        return "保存失败";
    }

    @Override
    public Blog getByIdBlog(Integer blogId) {
        return blogMapper.getBlogById(blogId);
    }

    private List<BlogListVO> getBlogListVOsByBlogs(List<Blog> blogList) {
        List<BlogListVO> blogListVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)) {
            List<Integer> categoryIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
            Map<Integer, String> blogCategoryMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(categoryIds)) {
                List<BlogCategory> blogCategories = blogCategoryMapper.selectByCategoryIds(categoryIds);
                if (!CollectionUtils.isEmpty(blogCategories)) {
                    blogCategoryMap = blogCategories.stream().collect(Collectors.toMap(BlogCategory::getCategoryId, BlogCategory::getCategoryIcon, (key1, key2) -> key2));
                }
            }
            for (Blog blog : blogList) {
                BlogListVO blogListVO = new BlogListVO();
                BeanUtils.copyProperties(blog, blogListVO);
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())) {
                    blogListVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                } else {
                    blogListVO.setBlogCategoryId(0);
                    blogListVO.setBlogCategoryName("默认分类");
                    blogListVO.setBlogCategoryIcon("/admin/dist/img/category/00.png");
                }
                blogListVOS.add(blogListVO);
            }
        }
        return blogListVOS;
    }
    @Override
    public BlogDetailVO getBlogDetail(Integer id) {
        Blog blog1 = new Blog();
        blog1.setBlogId(id);
        Blog blog = blogMapper.selectAllBlog(blog1);
        //不为空且状态为已发布
        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
        if (blogDetailVO != null) {
            return blogDetailVO;
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageByTag(String tagName, int page) {
        if (PatternUtil.validKeyword(tagName)) {
            BlogTag tag = blogTagMapper.selectByNameTag(tagName);
            if (tag != null && page > 0) {
                Map param = new HashMap();
                param.put("page", page);
                param.put("limit", 9);
                param.put("tagId", tag.getTagId());
                PageQueryUtil pageUtil = new PageQueryUtil(param);
                List<Blog> blogList = blogMapper.getBlogsPageByTagId(pageUtil);
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                int total = blogMapper.getTotalBlogs(pageUtil);
                PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
                return pageResult;
            }
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageByCategory(String categoryName, int page) {
        if (PatternUtil.validKeyword(categoryName)) {
            BlogCategory blogCategory = blogCategoryMapper.selectByName(categoryName);
            if ("默认分类".equals(categoryName) && blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
            }
            if (blogCategory != null && page > 0) {
                Map param = new HashMap();
                param.put("page", page);
                param.put("limit", 9);
                param.put("blogCategoryId", blogCategory.getCategoryId());
                param.put("blogStatus", 1);//过滤发布状态下的数据
                PageQueryUtil pageUtil = new PageQueryUtil(param);
                List<Blog> blogList = blogMapper.selectListBlog(pageUtil);
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                int total = blogMapper.getTotalBlogs(pageUtil);
                PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
                return pageResult;
            }
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageBySearch(String keyword, int page) {
        if (page > 0 && PatternUtil.validKeyword(keyword)) {
            Map param = new HashMap();
            param.put("page", page);
            param.put("limit", 9);
            param.put("keyword", keyword);
            param.put("blogStatus", 1);//过滤发布状态下的数据
            PageQueryUtil pageUtil = new PageQueryUtil(param);
            List<Blog> blogList = blogMapper.selectListBlog(pageUtil);
            List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
            int total = blogMapper.getTotalBlogs(pageUtil);
            PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
            return pageResult;
        }
        return null;
    }

    @Override
    public BlogDetailVO getBlogDetailBySubUrl(String subUrl) {
        Blog blog = blogMapper.selectBySubUrl(subUrl);
        //不为空且状态为已发布
        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
        if (blogDetailVO != null) {
            return blogDetailVO;
        }
        return null;
    }

    /**
     * 方法抽取
     *
     * @param blog
     * @return
     */
    private BlogDetailVO getBlogDetailVO(Blog blog) {
        if (blog != null && blog.getBlogStatus() == 1) {
            //增加浏览量
            blog.setBlogViews(blog.getBlogViews() + 1);
            blogMapper.updateByPrimaryKey(blog);
            BlogDetailVO blogDetailVO = new BlogDetailVO();
            BeanUtils.copyProperties(blog, blogDetailVO);
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            BlogCategory blogCategory = blogCategoryMapper.selectByCategoryId(blog.getBlogCategoryId());
            if (blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            //分类信息
            blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());
            if (StringUtils.hasText(blog.getBlogTags())) {
                //标签设置
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            //设置评论数
            Map params = new HashMap();
            params.put("blogId", blog.getBlogId());
            params.put("commentStatus", 1);//过滤审核通过的数据
            blogDetailVO.setCommentCount(blogCommentMapper.getCommentTotal());
            return blogDetailVO;
        }
        return null;
    }


}

