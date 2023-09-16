package com.hzj.service;

import java.util.Map;

/**
 * PackageName :com.hzj.service
 * ClassName: BlogConfigService
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/12  12:31
 * @edition 1.0
 */
public interface BlogConfigService {
    /**
     * 获取所有的配置项
     *
     * @return
     */
    Map<String,String> getAllConfigs();
    /**
     * 修改配置项
     *
     * @param configName
     * @param configValue
     * @return
     */
    int updateConfig(String configName, String configValue);
}
