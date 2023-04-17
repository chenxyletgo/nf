package vip.self.cliservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import vip.self.cliservice.entity.Category;
import vip.self.cliservice.entity.vo.CategoryVo;
import vip.self.cliservice.mapper.CategoryMapper;
import vip.self.cliservice.service.CategoryService;
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
 * @since 2023-04-10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<Category> firstCates() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("father_id", "0");
        List<Category> list = this.list(wrapper);
        return list;
    }

    @Override
    public List<Category> secondCatesByFatherId(String fatherId) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("father_id", fatherId);
        List<Category> list = this.list(wrapper);
        return list;
    }

    @Override
    public List<Category> secondCatesByPathName(String pathName) {
        QueryWrapper<Category> wrapper =  new QueryWrapper<>();
        wrapper.eq("path_name", pathName);
        Category fatherCate = this.getOne(wrapper);
        List<Category> list = secondCatesByFatherId(fatherCate.getId());
        return list;
    }

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
    public Category getCateByPathName(String pathName) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("path_name", pathName);
        Category category = this.getOne(wrapper);
        return category;
    }
}
