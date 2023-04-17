package vip.self.cliservice.entity.pro;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticlePro {

    private String id;
    private String title;
    private String text;
    private String cover;
    private String userId;
    private Integer watch;
    private String firstCategoryId;
    private String secondCategoryId;
    private Integer level;
    private Integer state;
    private Date createTime;
    private Date modifyTime;
    private Integer isDeleted;
    private Integer like;
    private Integer commentCount;

}
