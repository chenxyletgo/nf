package vip.self.manservice.entity.vo;

import lombok.Data;
import vip.self.manservice.entity.Category;
import vip.self.manservice.entity.User;

import java.util.Date;

@Data
public class ArticleVo {
    private String title;
    private String firstCategoryId;
    private String secondCategoryId;
    private String preDate;
    private String sufDate;
    private String level;
    private String state;
    /**
     * 文章列表搜索
     * title
     * first~
     * second~
     * pre~
     * suf~
     * level
     */
    private String id;
    private User user;
    private Integer watch;
    private Category firstCategory;
    private Category secondCategory;
    private Date modifyTime;
    /**
     * 文章列表返回对象
     * id
     * title
     * user
     * watch
     * firstCategory
     * secondCategory
     * level
     * modifyTime
     */

    private String text;
    /**
     * 文章预览
     * User
     * title
     * text
     */

    private String reason;
    /**
     * 审核文章
     * id reason
     */
    private String cover;
}
