package com.hzj.service;

import com.hzj.pojo.AdminUser;

/**
 * PackageName :com.hzj.service
 * ClassName: AdminUserService
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  17:11
 * @edition 1.0
 */
public interface AdminUserService {
    /**
     * 登录的账号比对
     * @param userName
     * @param password
     * @return
     */
    AdminUser loginComparison(String userName, String password);

    AdminUser getUserDetailById(Integer loginUserId);
}
