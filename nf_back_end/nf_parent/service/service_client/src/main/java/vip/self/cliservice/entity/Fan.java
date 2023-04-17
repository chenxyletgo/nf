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
 * @since 2023-04-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_fan")
public class Fan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关系id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 发布者id
     */
    @TableField("b_user_id")
    private String bUserId;

    /**
     * 关注者id
     */
    @TableField("f_user_id")
    private String fUserId;

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
