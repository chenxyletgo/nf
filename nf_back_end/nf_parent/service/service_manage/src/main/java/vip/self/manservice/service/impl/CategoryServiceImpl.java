package vip.self.manservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.self.manservice.entity.Category;
import vip.self.manservice.entity.vo.CategoryVo;
import vip.self.manservice.mapper.CategoryMapper;
import vip.self.manservice.other.util.MyUtil;
import vip.self.manservice.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 搜索类别
     * @param name 名称
     * @param level 一级还是二级，1表示一级，2表示二级
     * @param cur 当前页
     * @param size 每页数量
     * @return 分页对象
     */
    @Override
    public Page<Category> getCateList(String name, Integer level, Integer cur, Integer size) {
        Page<Category> page = new Page<>(cur, size);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.like(!MyUtil.strIsEmpty(name), "name", name);
        if (level == 1) wrapper.eq("father_id", "0");
        if (level == 2) wrapper.ne("father_id", "0");
        this.page(page, wrapper);
        return page;
    }

    /**
     * 删除类别
     * @param id 要删除的类别id
     * @return
     */
    @Override
    public Integer delCategory(String id) {
        Category category = this.getById(id);
        String temp = category.getPathName();
        if (category == null) return 20001;
        if (category.getFatherId().equals("0")) {// 一级分类
            Long sonCount = getSonCateCountById(category.getId());
            if (sonCount != 0) return 20002;
        }
        category.setDelUniqueKey(category.getId());
        boolean b1 = this.updateById(category);
        if (!b1) return 20001;
        boolean b2 = this.removeById(category);
        if (!b2) return 20001;
        if (temp != null) return 200001;
        else return 200002;
    }

    /**
     * 获取类别的树型结构
     * @return 数据
     */
    @Override
    public List<CategoryVo> getTreeList() {
        List<CategoryVo> cvs = new ArrayList<>();
        QueryWrapper<Category> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("father_id", "0");
        List<Category> firstCates = this.list(wrapper1); // 获取一级list
        if (firstCates != null) {
            for (Category c : firstCates){ // 遍历一级list
                CategoryVo cv = new CategoryVo();
                cv.setId(c.getId());
                cv.setName(c.getName());
                cvs.add(cv);
                QueryWrapper<Category> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("father_id", c.getId());
                List<Category> secondCates = this.list(wrapper2);// 获取二级list
                if (secondCates != null) {
                    cv.setChildren(new ArrayList<CategoryVo>());
                    for (Category sc : secondCates) { // 遍历二级list
                        CategoryVo cv_ = new CategoryVo();
                        cv_.setId(sc.getId());
                        cv_.setName(sc.getName());
                        cv.getChildren().add(cv_);
                    }
                }
            }
        }
        return cvs;
    }

    @Override
    public Long getSonCateCountById(String fatherId) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("father_id", fatherId);
        long count = this.count(wrapper);
        return count;
    }

}
