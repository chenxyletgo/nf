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
@TableName("t_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 分类名
     */
    @TableField("name")
    private String name;

    /**
     * 路径名
     */
    @TableField("path_name")
    private String pathName;

    /**
     * 0表示一级分类，非0表示二级分类
     */
    @TableField("father_id")
    private String fatherId;

    /**
     * 解决逻辑删除和唯一约束冲突
     */
    @TableField("del_unique_key")
    private String delUniqueKey;

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
