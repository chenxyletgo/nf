package vip.self.cliservice.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryVo {
    private String id;
    private String name;
    private String pathName;
    private List<CategoryVo> children;
}
