package vip.self.cliservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author cld
 * @since 2023-04-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户昵称
     */
    @TableField("name")
    private String name;

    /**
     * 密码加盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 解决逻辑删除和唯一约束冲突
     */
    @TableField("del_unique_key")
    private String delUniqueKey;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 留言
     */
    @TableField("message")
    private String message;

    /**
     * 性别，0女，1男，2保密
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 居住地
     */
    @TableField("habitual_residence")
    private String habitualResidence;

    /**
     * 生日
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 账号状态，0正常，1禁言，2封禁，3管理员
     */
    @TableField("state")
    private Integer state;

    /**
     * 创建日期
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改日期
     */
    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

    /**
     * 逻辑删除，0存在，1不存在
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;


}
