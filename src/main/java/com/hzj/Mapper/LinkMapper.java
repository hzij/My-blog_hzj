package com.hzj.Mapper;

import com.hzj.pojo.Link;
import com.hzj.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: LinkMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/13  16:13
 * @edition 1.0
 */
@Mapper
public interface LinkMapper {
    //查询link表的数据数量
    int selectCount();
    //查询所有的数据
    List<Link> selectLinkList(PageQueryUtil pageQueryUtil);
    //进行url比对查看是否有这个友链
    Link selectLinkByName(Link link);
    //进行友链数据的添加
    int insertLink(Link link);
    //修改友链
    int updateLink(Link link);
    //通过id查询友链信息
    Link selectLinkById(Link link);
    //通过修改is_deleted字段实现删除
    int  deleteLink(Integer[] ids);
}
