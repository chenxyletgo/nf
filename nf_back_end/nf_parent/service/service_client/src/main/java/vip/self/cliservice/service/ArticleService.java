package vip.self.cliservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.self.cliservice.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.self.cliservice.entity.pro.ArticlePro;
import vip.self.cliservice.entity.vo.ArticleVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
public interface ArticleService extends IService<Article> {

    public abstract Page<ArticlePro> getArticleList(String title,
                                                    String firstCatePathName,
                                                    String secondCateId,
                                                    Integer state,
                                                    Integer sort,
                                                    Integer cur,
                                                    Integer size);

    public abstract ArticleVo ap2av(ArticlePro ap, String curUserId);

    public abstract List<ArticleVo> aps2avs(List<ArticlePro> aps, String curUserId);

    public abstract Long getArticleCountByUserId(String userId);

    public abstract ArticlePro getArticleProById(String id, Integer state);

    public abstract Page<ArticlePro> getArticleList(String userId,
                                                    Integer state,
                                                    Integer cur,
                                                    Integer size);

    public abstract List<Article> getAllArticleByUserId(String userId);

    public abstract List<Article> getTopArticleList();

}
