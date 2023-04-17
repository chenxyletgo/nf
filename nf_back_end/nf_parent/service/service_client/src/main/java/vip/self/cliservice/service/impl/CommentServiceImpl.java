package vip.self.cliservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import vip.self.cliservice.entity.Comment;
import vip.self.cliservice.entity.User;
import vip.self.cliservice.entity.pro.CommentPro;
import vip.self.cliservice.entity.vo.CommentVo;
import vip.self.cliservice.mapper.CommentMapper;
import vip.self.cliservice.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.self.cliservice.service.LikeCService;
import vip.self.cliservice.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cld
 * @since 2023-04-11
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired(required = false)
    private CommentMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeCService likeCService;

    /**
     *
     * @param id id值可以未文章 也可以未评论
     * @param level  0一级 1二级和三级
     * @param sort 0喜欢 1时间排序
     * @param cur
     * @param size
     * @return
     */
    @Override
    public Page<CommentPro> getCommentList(String id, Integer level, Integer sort, Integer cur, Integer size) {
        Page<CommentPro> page = new Page<>(cur, size);
        QueryWrapper<CommentPro> wrapper = new QueryWrapper<>();
        wrapper.eq("reply_type", level);
        wrapper.eq("reply_id", id);
        if (sort == 0) wrapper.orderByDesc("like_count").orderByAsc("id");
        if (sort == 1) wrapper.orderByDesc("modify_time").orderByAsc("id");
        wrapper.eq("is_deleted", 0);
        mapper.selectCommentPro(page, wrapper);
        return page;
    }

    @Override
    public CommentVo cp2cv(CommentPro cp, String curUserId) {
        CommentVo cv = new CommentVo();
        cv.setId(cp.getId());
        cv.setUserId(cp.getSUserId());
        User user = userService.getById(cp.getSUserId());
        cv.setUserName(user.getName());
        cv.setUserAvatar(user.getAvatar());
        cv.setWords(cp.getWords());
        cv.setTime(cp.getModifyTime());
        cv.setLike(cp.getLikeCount());
        Long disLikeCount = likeCService.getDislikeCountByCommentId(cp.getId());
        cv.setDislike(disLikeCount);
        Integer integer = likeCService.curUserIsComm(curUserId, cp.getId());
        if (integer == 0) cv.setActive(false);
        if (integer == 1) cv.setActive(true);
        // 新增
        if (cp.getReplyType() == 1) {
            cv.setFatherId(cp.getReplyId());
            if (cp.getRUserId() != null) {
                User ruser = userService.getById(cp.getRUserId());
                cv.setRuserId(cp.getRUserId());
                cv.setRuserName(ruser.getName());
            }
        }
        return cv;
    }

    @Override
    public List<CommentVo> cps2cvs(List<CommentPro> cps, String curUserId) {
        List<CommentVo> list = new ArrayList<>();
        for (int i = 0; i < cps.size(); i++) {
            CommentVo cv = cp2cv(cps.get(i), curUserId);
            list.add(cv);
        }
        return list;
    }

    @Override
    public Page<Comment> getCommentListByArticle(String articleId, Integer cur, Integer size) {
        Page<Comment> page = new Page<>(cur, size);
        mapper.selectCommentByArticleId(articleId, page);
        return page;
    }

    public CommentVo c2cv(Comment c) {
        CommentVo cv = new CommentVo();
        cv.setId(c.getId());
        cv.setUserId(c.getSUserId());
        User user = userService.getById(c.getSUserId());
        cv.setUserName(user.getName());
        cv.setWords(c.getWords());
        cv.setTime(c.getModifyTime());
        cv.setLike(likeCService.getLikeCountByCommentId(c.getId()));
        cv.setDislike(likeCService.getDislikeCountByCommentId(c.getId()));
        cv.setLevel(c.getReplyType().longValue());
        return cv;
    }

    @Override
    public List<CommentVo> cs2cvs(List<Comment> cs) {
        List<CommentVo> list = cs.stream().map(new Function<Comment, CommentVo>() {
            @Override
            public CommentVo apply(Comment c) {
                CommentVo cv = c2cv(c);
                return cv;
            }
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public Long SonCommCountByFirstCommId(String firstCommId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("reply_type", 1);
        wrapper.eq("reply_id", firstCommId);
        long count = this.count(wrapper);
        return count;
    }
}
