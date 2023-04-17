package vip.self.manservice.entity.vo;

import lombok.Data;

@Data
public class UserVo {
    private String name;
    private String sex;
    private String preDate;
    private String sufDate;
    private String habitualResidence;
    private String state;
    /**
     * 用户查询
     * name sex preDate sufDate habitualResidence state
     */
}
