package vip.self.cliservice.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CommentVo {

    private String id;
    private String userId;
    private String userName;
    private String userAvatar;
    private String words;
    private Date time;
    private Long like;
    private Long dislike;
    private Boolean active;
    private Long level;

    /**
     * 一级评论返回 上方全部
     */
    private Long sort;
    /**
     * 一级评论获取
     * id sort level
     * 这里的id是文章的id
     */
    /**
     * 二级评论获取
     * id sort level
     * 这里id是评论id
     */
    private String fatherId;
    private String ruserId;
    private String ruserName;
    /**
     * 二级评论返回
     * id fahterId userId userAvatar userName
     * ruserId ruserName words time like dislike
     * active
     */

    /**
     * 评论添加
     * id ruserId words
     */
    /**
     * 评论管理
     * id userId userName words
     * time like dislike
     */
}
