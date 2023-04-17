package vip.self.manservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.self.manservice.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.self.manservice.entity.Category;
import vip.self.manservice.entity.User;
import vip.self.manservice.entity.vo.ArticleVo;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
public interface ArticleService extends IService<Article> {

    /**
     * 文章搜索
     * @param title 文章标题
     * @param firstCategoryId 一级分类
     * @param secondCategoryId 二级分类
     * @param preDate 起始时间-修改
     * @param sufDate 结束时间-修改
     * @param level 文章级别 0正常 1推荐 2置顶
     * @param state 文章状态 0草稿 1审核中 2审核通过 3审核未通过
     * @param cur 当前页
     * @param size 每页数量
     * @return 分页对象
     */
    public abstract Page<Article> getArticleList(String title,
                                                 String firstCategoryId,
                                                 String secondCategoryId,
                                                 String preDate,
                                                 String sufDate,
                                                 Integer level,
                                                 Integer state,
                                                 Integer cur,
                                                 Integer size);

    /**
     * 将Article转ArticleVo
     * @param articles a集合
     * @return av集合
     */
    public abstract List<ArticleVo> a2av(List<Article> articles);

    /**
     * 根据userId获取用户对象
     * @param userId
     * @return
     */
    public abstract User getUserById(String userId);

    public abstract Category getCategoryById(String cateId);

    public abstract boolean setMessage(String... args);

    public abstract boolean delCateInArticle(String cateId, Integer level);

}
