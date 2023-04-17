package vip.self.cliservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.self.cliservice.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.self.cliservice.entity.pro.CommentPro;
import vip.self.cliservice.entity.vo.CommentVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-11
 */
public interface CommentService extends IService<Comment> {

    /**
     *
     * @param articleId
     * @param level  0一级 1二级和三级
     * @param sort 0喜欢 1排序
     * @param cur
     * @param size
     * @return
     */
    Page<CommentPro> getCommentList(String articleId, Integer level, Integer sort, Integer cur, Integer size);

    CommentVo cp2cv(CommentPro cp, String curUserId);

    List<CommentVo> cps2cvs(List<CommentPro> cps, String curUserId);

    Page<Comment> getCommentListByArticle(String articleId, Integer cur, Integer size);

    CommentVo c2cv(Comment c);

    List<CommentVo> cs2cvs(List<Comment> cs);

    Long SonCommCountByFirstCommId(String firstCommId);

}
