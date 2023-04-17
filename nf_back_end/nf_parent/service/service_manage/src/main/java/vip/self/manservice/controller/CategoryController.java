package vip.self.manservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.manservice.entity.Category;
import vip.self.manservice.entity.vo.CategoryVo;
import vip.self.manservice.other.util.MyUtil;
import vip.self.manservice.service.ArticleService;
import vip.self.manservice.service.CategoryService;
import vip.self.uticommon.result.R;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
@RestController
@RequestMapping("/manservice/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Autowired
    private ArticleService articleService;

    /**
     * 获取类别列表
     * @param cv 搜索条件
     * @param cur 当前页
     * @param size 每页大小
     * @return 响应
     */
    @PostMapping("/adm/getCateList/{cur}/{size}")
    public R getCateList(@RequestBody CategoryVo cv,
                         @PathVariable(value = "cur") Long cur,
                         @PathVariable(value = "size") Long size) {
        if (MyUtil.strIsEmpty(cv.getLevel())) cv.setLevel("-1");
        Page<Category> page =
                service.getCateList(cv.getName(),
                        Integer.valueOf(cv.getLevel()),
                        cur.intValue(),
                        size.intValue());
        return R.ok().data("list", page.getRecords())
                .data("total", page.getTotal());
    }

    /**
     * 获取所有一级类别
     * @return 响应
     */
    @GetMapping("/adm/getFirstCateList")
    public R getFirstCateList() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("father_id", "0");
        List<Category> list = service.list(wrapper);
        return R.ok().data("list", list);
    }

    /**
     * 添加类别
     * @param category 类别数据
     * @return 响应
     */
    @PostMapping("/adm/addCategory")
    public R addCategory(@RequestBody Category category) {
        if (category.getPathName() != null) {
            boolean b1 = MyUtil.matcherAlphabet(category.getPathName());
            if (!b1) return R.error().setMessage("路径名不合法");
        }
        boolean b2 = service.save(category);
        if (!b2) return R.error().setMessage("创建失败");
        return R.ok().setMessage("创建成功");
    }

    /**
     * 删除类别
     * @param category 要删除的类别
     * @return 响应
     */
    @DeleteMapping("/adm")
    public R delCategory(@RequestBody Category category) {
        String temp = category.getId();
        Integer code = service.delCategory(category.getId());
        switch (code) {
            case 200001: {
                boolean b = articleService.delCateInArticle(temp, 200001);
                if (!b) return R.error().setMessage("删除失败");
                return R.ok().setMessage("删除成功");
            }
            case 200002: {
                boolean b = articleService.delCateInArticle(temp, 200002);
                if (!b) return R.error().setMessage("删除失败");
                return R.ok().setMessage("删除成功");
            }
            case 20001: return R.error().setMessage("删除失败");
            case 20002: return R.error().setMessage("该类别下有子类，需要先删除子类");
            default: return R.error().setMessage("删除失败");
        }
    }

    /**
     * 获取数型结构数据
     * @return 响应
     */
    @GetMapping("/adm/getTreeList")
    public R getTreeList() {
        List<CategoryVo> list = service.getTreeList();
        return R.ok().data("list", list);
    }

    /**
     * 根据id获取类别
     * @param id
     * @return
     */
    @GetMapping("/adm/{id}")
    public R getCateById(@PathVariable(value = "id") String id) {
        Category category = service.getById(id);
        if (category == null) return R.error().setMessage("该类别不存在");
        return R.ok().data("category", category);
    }

    /**
     * 修改类别
     * @param category 要修改的类别
     * @return 响应
     */
    @PutMapping("/adm")
    public R editCateById(@RequestBody Category category) {
        if ("-1".equals(category.getPathName())) {
            // 二级类别修改
            category.setPathName(null);
            boolean b = service.updateById(category);
            if (!b) return R.error().setMessage("修改失败");
            return R.ok().setMessage("修改成功");
        }
        // 一级类别修改
        if (MyUtil.strIsEmpty(category.getPathName()) || !MyUtil.matcherAlphabet(category.getPathName())) return R.error().setMessage("路径名称不合法");
        boolean b = service.updateById(category);
        if (!b) return R.error().setMessage("修改失败");
        return R.ok().setMessage("修改成功");
    }

}
