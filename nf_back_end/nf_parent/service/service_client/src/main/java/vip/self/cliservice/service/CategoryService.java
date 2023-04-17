package vip.self.cliservice.service;

import org.omg.CORBA.PUBLIC_MEMBER;
import vip.self.cliservice.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.self.cliservice.entity.vo.CategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
public interface CategoryService extends IService<Category> {

    public abstract List<Category> firstCates();

    public abstract List<Category> secondCatesByFatherId(String fatherId);

    public abstract List<Category> secondCatesByPathName(String pathName);

    public abstract List<CategoryVo> getTreeList();

    public abstract Category getCateByPathName(String pathName);

}
