package com.hzj.pojo;

import lombok.Data;

/**
 * PackageName :com.hzj.pojo
 * ClassName: tbAdminUser
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  15:34
 * @edition 1.0
 */
@Data
public class AdminUser {
        private Integer adminUserId; //管理员id
        private String loginUserName; //管理员登录名称
        private String loginPassword; //管理员登录密码
        private String nickName; //管理员显示名称
        private String locked; //是否锁定，0未锁定 ，1已锁定无法登录
        @Override
        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(getClass().getSimpleName());
                sb.append(" [");
                sb.append("Hash = ").append(hashCode());
                sb.append(", adminUserId=").append(adminUserId);
                sb.append(", loginUserName=").append(loginUserName);
                sb.append(", loginPassword=").append(loginPassword);
                sb.append(", nickName=").append(nickName);
                sb.append(", locked=").append(locked);
                sb.append("]");
                return sb.toString();
        }
}
