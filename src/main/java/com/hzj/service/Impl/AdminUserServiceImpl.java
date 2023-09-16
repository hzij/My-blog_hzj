package com.hzj.service.Impl;

import com.hzj.Mapper.AdminUserMapper;
import com.hzj.pojo.AdminUser;
import com.hzj.util.MD5Util;
import com.hzj.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PackageName :com.hzj.service.Impl
 * ClassName: AdminUserServiceImpl
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  17:12
 * @edition 1.0
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper AdminUserMapper;

    /**
     * 进行用户名和密码的比对
     * @param userName
     * @param password
     * @return
     */
    @Override
    public AdminUser loginComparison(String userName, String password) {
        String MD5password = MD5Util.MD5Encode(password, "UTF-8");
        AdminUser adminUser = AdminUserMapper.selectLogin(userName, MD5password);
        return adminUser;
    }

    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {
        AdminUser adminUser = AdminUserMapper.selectById(loginUserId);
        return adminUser;
    }
}
