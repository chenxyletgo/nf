package vip.self.manservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.self.manservice.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.self.manservice.entity.vo.CategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
public interface CategoryService extends IService<Category> {

    /**
     * 搜索类别
     * @param name 名称
     * @param level 一级还是二级，1表示一级，2表示二级
     * @param cur 当前页
     * @param size 每页数量
     * @return 分页对象
     */
    public abstract Page<Category> getCateList(String name, Integer level, Integer cur, Integer size);

    /**
     * 删除类别
     * @param id 要删除的类别id
     * @return 成功200001 成功200002 失败20001 有子类20002
     */
    public abstract Integer delCategory(String id);

    /**
     * 获取分类的树型结构数据
     * @return
     */
    public abstract List<CategoryVo> getTreeList();

    public abstract Long getSonCateCountById(String fatherId);

}
