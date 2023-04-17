package vip.self.cliservice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.self.cliservice.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.self.cliservice.entity.pro.ArticlePro;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
public interface ArticleMapper extends BaseMapper<Article> {

    Page<ArticlePro> selectArticlePro(Page<ArticlePro> page, @Param(Constants.WRAPPER) QueryWrapper<ArticlePro> queryWrapper);

    ArticlePro selectArticlePro(@Param(Constants.WRAPPER) QueryWrapper<ArticlePro> queryWrapper);

}
