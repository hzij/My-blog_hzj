package com.hzj.service.Impl;

import com.hzj.Mapper.BlogConfigMapper;
import com.hzj.pojo.BlogConfig;
import com.hzj.service.BlogConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * PackageName :com.hzj.service.Impl
 * ClassName: BlogConfigServiceImpl
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  12:31
 * @edition 1.0
 */
@Service
public class BlogConfigServiceImpl implements BlogConfigService {
    @Autowired
    private BlogConfigMapper configMapper;

    public static final String websiteName = "personal blog";
    public static final String websiteDescription = "personal blog是SpringBoot2+Thymeleaf+Mybatis建造的个人博客网站.SpringBoot实战博客源码.个人博客搭建";
    public static final String websiteLogo = "/admin/dist/img/logo2.png";
    public static final String websiteIcon = "/admin/dist/img/favicon.png";

    public static final String yourAvatar = "/admin/dist/img/13.png";
    public static final String yourEmail = "1805151448@qq.com";
    public static final String yourName = "Hzj";

    public static final String footerAbout = "your personal blog. have fun.";
    public static final String footerICP = "浙ICP备 xxxxxx-x号";
    public static final String footerCopyRight = "@2018 hzj";
    public static final String footerPoweredBy = "personal blog";
    public static final String footerPoweredByURL = "##";

    @Override
    public Map<String, String> getAllConfigs() {
        //获取所有的map并封装为map
        List<BlogConfig> blogConfigs = configMapper.getConfigAll();
        Map<String, String> configMap = blogConfigs.stream().collect(Collectors.toMap(BlogConfig::getConfigName, BlogConfig::getConfigValue));
        for (Map.Entry<String, String> config : configMap.entrySet()) {
            if ("websiteName".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(websiteName);
            }
            if ("websiteDescription".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(websiteDescription);
            }
            if ("websiteLogo".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(websiteLogo);
            }
            if ("websiteIcon".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(websiteIcon);
            }
            if ("yourAvatar".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(yourAvatar);
            }
            if ("yourEmail".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(yourEmail);
            }
            if ("yourName".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(yourName);
            }
            if ("footerAbout".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(footerAbout);
            }
            if ("footerICP".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(footerICP);
            }
            if ("footerCopyRight".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(footerCopyRight);
            }
            if ("footerPoweredBy".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(footerPoweredBy);
            }
            if ("footerPoweredByURL".equals(config.getKey()) && !StringUtils.hasText(config.getValue())) {
                config.setValue(footerPoweredByURL);
            }
        }
        return configMap;
    }

    @Override
    public int updateConfig(String configName, String configValue) {
        BlogConfig blogConfig = configMapper.selectByPrimaryKey(configName);
        if (blogConfig != null) {
            blogConfig.setConfigValue(configValue);
            blogConfig.setUpdateTime(String.valueOf(new Date()));
            return configMapper.updateByPrimaryKeySelective(blogConfig);
        }
        return 0;
    }
}
