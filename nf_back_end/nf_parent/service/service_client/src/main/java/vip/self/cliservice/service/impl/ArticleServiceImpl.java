package vip.self.cliservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import vip.self.cliservice.entity.Article;
import vip.self.cliservice.entity.Category;
import vip.self.cliservice.entity.User;
import vip.self.cliservice.entity.pro.ArticlePro;
import vip.self.cliservice.entity.vo.ArticleVo;
import vip.self.cliservice.mapper.ArticleMapper;
import vip.self.cliservice.other.util.MyUtil;
import vip.self.cliservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.self.concommon.exception.NFException;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired(required = false)
    private ArticleMapper mapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeAService likeAService;

    @Autowired
    private FanService fanService;

    @Override
    public Page<ArticlePro> getArticleList(String title, String firstCatePathName, String secondCateId, Integer state, Integer sort, Integer cur, Integer size) {
        Page<ArticlePro> page = new Page<>(cur, size);
        QueryWrapper<ArticlePro> wrapper = new QueryWrapper<>();
        wrapper.like(!MyUtil.strIsEmpty(title), "title", title);
        if (!MyUtil.strIsEmpty(firstCatePathName)) {
            if ("recommend".equals(firstCatePathName)) wrapper.eq("level", 1);
            if ("other".equals(firstCatePathName)) {
                wrapper.isNull("first_category_id");
                wrapper.isNull("second_category_id");
            }
            Category cate = categoryService.getCateByPathName(firstCatePathName);
            if (cate != null) wrapper.eq("first_category_id", cate.getId());
        }
        if (!MyUtil.strIsEmpty(secondCateId)) wrapper.eq("second_category_id", secondCateId);
        if (state == 0 || state == 1 || state == 2 || state == 3) wrapper.eq("state", state);
        switch (sort) {
            case 0: break;
            case 1: wrapper.orderByDesc("modify_time").orderByAsc("id"); break;
            case 2: wrapper.orderByDesc("watch").orderByAsc("id"); break;
            case 3: wrapper.orderByDesc("`like`").orderByAsc("id"); break;
            case 4: wrapper.orderByDesc("comment_count").orderByAsc("id");
        }
        mapper.selectArticlePro(page, wrapper);
        return page;
    }

    @Override
    public ArticleVo ap2av(ArticlePro ap, String curUserId) {
        ArticleVo av = new ArticleVo();
        av.setId(ap.getId()).setTitle(ap.getTitle())
                .setText(ap.getText()).setCover(ap.getCover())
                .setUserId(ap.getUserId());
        User user = userService.getById(ap.getUserId());
        av.setUserName(user.getName()).setUserAvatar(user.getAvatar());
        if (ap.getSecondCategoryId() != null) {
            Category cate = categoryService.getById(ap.getSecondCategoryId());
            av.setSecondCateId(ap.getSecondCategoryId());
            av.setSecondCateName(cate.getName());
        }
        av.setWatch(ap.getWatch()).setLike(ap.getLike())
                .setCommentCount(ap.getCommentCount());
        boolean b = likeAService.userLikeArticle(curUserId, ap.getId());
        av.setLikeActive(b);
        // 增加数据 文章展示
        av.setTime(ap.getModifyTime());
        if (ap.getFirstCategoryId() != null) {
            Category cate = categoryService.getById(ap.getFirstCategoryId());
            av.setFirstCateId(ap.getFirstCategoryId());
            av.setFirstCateName(cate.getName());
            av.setFirstCatePathName(cate.getPathName());
        }
        Long articleCount = this.getArticleCountByUserId(ap.getUserId());
        av.setUserArticleCount(articleCount);
        Long fanCount = fanService.getFanCountByUserId(ap.getUserId());
        av.setUserFanCount(fanCount);
        boolean isFan = fanService.isFan(ap.getId(), curUserId);
        av.setFanActive(isFan);
        // 增加数据 文章编辑页面
        av.setState(ap.getState());
        return av;
    }

    @Override
    public List<ArticleVo> aps2avs(List<ArticlePro> aps, String curUserId) {
        List<ArticleVo> list = new ArrayList<>();
        for (int i = 0; i < aps.size(); i++) {
            ArticleVo av = ap2av(aps.get(i), curUserId);
            list.add(av);
        }
        return list;
    }

    @Override
    public Long getArticleCountByUserId(String userId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        long count = this.count(wrapper);
        return count;
    }

    @Override
    public ArticlePro getArticleProById(String id, Integer state) {
        QueryWrapper<ArticlePro> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        if (state == 2) wrapper.eq("state", state);
        ArticlePro articlePro = mapper.selectArticlePro(wrapper);
        return articlePro;
    }

    /**
     * 查询一个用户的所有文章
     * @param userId
     * @param state
     * @param cur
     * @param size
     * @return
     */
    @Override
    public Page<ArticlePro> getArticleList(String userId, Integer state, Integer cur, Integer size) {
        Page<ArticlePro> page = new Page<>(cur, size);
        QueryWrapper<ArticlePro> wrapper = new QueryWrapper<>();
        if (!MyUtil.strIsEmpty(userId)) wrapper.eq("user_id", userId);
        if (state == 0 || state == 1 || state == 2 || state == 3) wrapper.eq("state", state);
        wrapper.orderByDesc("modify_time").orderByAsc("id");
        mapper.selectArticlePro(page, wrapper);
        return page;
    }

    @Override
    public List<Article> getAllArticleByUserId(String userId) {
        if (MyUtil.strIsEmpty(userId)) return null;
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("state", 2);
        List<Article> list = this.list(wrapper);
        return list;
    }

    @Override
    public List<Article> getTopArticleList() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("level", 2);
        List<Article> list = this.list(wrapper);
        return list;
    }
}
