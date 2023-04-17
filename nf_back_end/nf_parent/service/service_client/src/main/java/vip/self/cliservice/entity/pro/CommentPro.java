package vip.self.cliservice.entity.pro;

import lombok.Data;
import java.util.Date;

@Data
public class CommentPro {
    private String id;
    private String words;
    private Integer replyType;
    private String replyId;
    private String sUserId;
    private String rUserId;
    private Date createTime;
    private Date modifyTime;
    private Integer isDeleted;
    private Long likeCount;
}
