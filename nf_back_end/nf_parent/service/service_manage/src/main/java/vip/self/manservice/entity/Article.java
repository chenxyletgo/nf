package vip.self.manservice.entity;

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
 * @since 2023-04-06
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 正文
     */
    @TableField("text")
    private String text;

    /**
     * 封面
     */
    @TableField("cover")
    private String cover;

    /**
     * 发布者
     */
    @TableField("user_id")
    private String userId;

    /**
     * 观看次数
     */
    @TableField("watch")
    private Integer watch;

    /**
     * 一级分类
     */
    @TableField("first_category_id")
    private String firstCategoryId;

    /**
     * 二级分类
     */
    @TableField("second_category_id")
    private String secondCategoryId;

    /**
     * 文章级别，0默认，1推荐，2置顶（轮播图）
     */
    @TableField("level")
    private Integer level;

    /**
     * 文章状态，0草稿，1审核中，2审核通过，3审核未通过
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
