package vip.self.cliservice.entity.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Setter
@Getter
@Accessors(chain = true)
public class ArticleVo {
    private String id;
    private String title;
    private String text;
    private String cover;
    private String userId;
    private String userName;
    private String userAvatar;
    private String secondCateId;
    private String secondCateName;
    private Integer watch;
    private Integer like;
    private Integer commentCount;
    private Boolean likeActive;
    /**
     * 文章div数据
     * 上方所有属性
     */
    private String pathName;
    private Long sort;
    /**
     * 文章div搜索
     * secondCateId pathName sort
     */
    private Date time;
    private Long userFanCount;
    private Boolean fanActive;
    private Long userArticleCount;
    private String firstCateId;
    private String firstCateName;
    private String firstCatePathName;
    /**
     * 文章页面
     * id title text userId userAvatar userName
     * time watch like likeActive commentCount
     * userFanCount userArticleCount firstCateId
     * firstCateName firstCatePathName fanActive
     */
    private Integer state;
    /**
     * 编辑文章
     */

    /**
     * 预览
     * id firstCateName title text username
     * useravatar
     */
}
