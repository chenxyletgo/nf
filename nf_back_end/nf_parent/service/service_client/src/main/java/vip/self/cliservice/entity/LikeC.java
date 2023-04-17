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
@TableName("t_like_c")
public class LikeC implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关系id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 评论id
     */
    @TableField("comment_id")
    private String commentId;

    /**
     * 持有态度者id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 0踩，1赞
     */
    @TableField("attitude")
    private Integer attitude;

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
