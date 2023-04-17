package vip.self.cliservice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.self.cliservice.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.self.cliservice.entity.pro.CommentPro;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cld
 * @since 2023-04-11
 */
public interface CommentMapper extends BaseMapper<Comment> {

    Page<CommentPro> selectCommentPro(Page<CommentPro> page, @Param(Constants.WRAPPER) QueryWrapper<CommentPro> queryWrapper);

    CommentPro selectCommentPro(@Param(Constants.WRAPPER) QueryWrapper<CommentPro> queryWrapper);

    Page<Comment> selectCommentByArticleId(@Param("articleId") String articleId, Page<Comment> page);
}
