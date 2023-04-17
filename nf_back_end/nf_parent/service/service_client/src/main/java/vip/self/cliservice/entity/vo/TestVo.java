package vip.self.cliservice.entity.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestVo {
    private String userId;
    private String articleId;
    /**
     * 添加喜欢 userId articleId
     */
    private String words;
    /**
     * 添加一级评论
     * userId articleId words
     */
}
