package vip.self.cliservice.entity.vo;

import lombok.Data;

@Data
public class UserVo {

    private String name;
    private String email;
    private String password;
    private String emailCode;

    /**
     * 用户注册
     * name email password emailCode
     */

    private String checkCode;
    /**
     * 用户登录
     * email password checkCode
     */
    private String id;
    private String avatar;
    private String message;
    private Long fan;
    private Long follow;
    private Integer sex;
    private String habitualResidence;
    private String birthday;
    /**
     * 获取用户信息
     * id name avatar message fan follow
     * sex habitualResidence birthday
     */
}
