package com.hzj.Controller.admin;

import com.hzj.pojo.Link;
import com.hzj.service.LinkService;
import com.hzj.util.PageQueryUtil;
import com.hzj.util.PageResult;
import com.hzj.util.Result;
import com.hzj.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * PackageName :com.hzj.Controller.admin
 * ClassName: BlogLinkController
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/13  13:42
 * @edition 1.0
 */
@RequestMapping("/admin")
@Controller
public class BlogLinkController {
    @Resource
    private LinkService linkService;
    @GetMapping("/links")
    public String toLink(HttpServletRequest request){
        request.setAttribute("path", "links");
        return "admin/link";
    }

    @GetMapping("links/list")
    @ResponseBody
    public Result linkList(@RequestParam Map<String,Object> params){
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))){
            return ResultGenerator.genFailResult("参数错误");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        PageResult pageResult = linkService.linkPage(pageQueryUtil);
        return ResultGenerator.genSuccessResult(pageResult);

    }
    @PostMapping("/links/save")
    @ResponseBody
    public Result saveLink(@RequestParam("linkType") Integer linkType,
                           @RequestParam("linkName") String linkName,
                           @RequestParam("linkUrl") String linkUrl,
                           @RequestParam("linkRank") Integer linkRank,
                           @RequestParam("linkDescription") String linkDescription){
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || !StringUtils.hasText(linkName) || !StringUtils.hasText(linkName) || !StringUtils.hasText(linkUrl) || !StringUtils.hasText(linkDescription)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        Link link = new Link();
        link.setLinkType(linkType);
        link.setLinkName(linkName);
        link.setLinkRank(linkRank);
        link.setLinkUrl(linkUrl);
        link.setLinkDescription(linkDescription);
        return ResultGenerator.genSuccessResult(linkService.saveLink(link));
    }
    @GetMapping("links/info/{id}")
    @ResponseBody
    public Result listUpdate(@PathVariable("id") Integer id){
        Link link = linkService.selectLinkById(id);
        return ResultGenerator.genSuccessResult(link);
    }
    @PostMapping("/links/update")
    @ResponseBody
    public Result updateLink( @RequestParam("linkId") Integer linkId,
                             @RequestParam("linkType") Integer linkType,
                             @RequestParam("linkName") String linkName,
                             @RequestParam("linkUrl") String linkUrl,
                             @RequestParam("linkRank") Integer linkRank,
                             @RequestParam("linkDescription") String linkDescription){
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || !StringUtils.hasText(linkName) || !StringUtils.hasText(linkName) || !StringUtils.hasText(linkUrl) || !StringUtils.hasText(linkDescription)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        Link link = new Link();
        link.setLinkId(linkId);
        link.setLinkType(linkType);
        link.setLinkName(linkName);
        link.setLinkRank(linkRank);
        link.setLinkUrl(linkUrl);
        link.setLinkDescription(linkDescription);
        return ResultGenerator.genSuccessResult(linkService.updateLink(link ));
    }


    @PostMapping("links/delete")
    @ResponseBody
    public Result deleteLink(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数错误");
        }
        if (linkService.deletedLink(ids)){
            return ResultGenerator.genSuccessResult("删除成功");
        }else {
            return  ResultGenerator.genFailResult("删除失败");
        }
    }


}
