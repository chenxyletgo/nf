package vip.self.cliservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.self.cliservice.entity.Category;
import vip.self.cliservice.entity.vo.CategoryVo;
import vip.self.cliservice.service.CategoryService;
import vip.self.uticommon.result.R;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
@RestController
@RequestMapping("/cliservice/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/getFirstCates")
    public R getFirstCates() {
        List<Category> list = service.firstCates();
        return R.ok().data("list", list);
    }

    @GetMapping("/getSecondCates/{pathName}")
    public R getSecondCates(@PathVariable(value = "pathName") String pathName) {
        List<Category> list = service.secondCatesByPathName(pathName);
        return R.ok().data("list", list);
    }

    @GetMapping("/getTreeCates")
    public R getTreeCates() {
        List<CategoryVo> list = service.getTreeList();
        return R.ok().data("list", list);
    }

}
