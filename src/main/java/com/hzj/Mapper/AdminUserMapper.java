package com.hzj.Mapper;

import com.hzj.pojo.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: tbAminUserMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  15:50
 * @edition 1.0
 */
@Mapper
public interface AdminUserMapper {
    /**
     * 进行用户名和密码的比对
     * @param userName
     * @param password
     * @return
     */
    AdminUser selectLogin(String userName, String password);
    /**
     * 通过id查询数据
     */
    AdminUser selectById(Integer userId);
}
