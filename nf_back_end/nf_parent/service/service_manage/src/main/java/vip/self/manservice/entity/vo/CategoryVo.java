package vip.self.manservice.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVo {
    private String id;
    private String name;
    private String level;
    /**
     * 分类查询
     * name level(1表示一级，2表示二级)
     */
    private List<CategoryVo> children;
    /**
     * 类别树型展示
     * id name children
     */
}
