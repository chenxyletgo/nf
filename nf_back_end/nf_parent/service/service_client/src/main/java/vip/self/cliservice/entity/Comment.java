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
 * @since 2023-04-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 评论内容
     */
    @TableField("words")
    private String words;

    /**
     * 回复对象类型，0文章，1评论
     */
    @TableField("reply_type")
    private Integer replyType;

    /**
     * 该字段根据reply_type字段值决定意义
     */
    @TableField("reply_id")
    private String replyId;

    /**
     * 发起者
     */
    @TableField("s_user_id")
    private String sUserId;

    /**
     * 接收者
     */
    @TableField("r_user_id")
    private String rUserId;

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
