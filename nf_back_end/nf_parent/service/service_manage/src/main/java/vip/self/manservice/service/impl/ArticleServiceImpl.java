package vip.self.manservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import vip.self.manservice.entity.Article;
import vip.self.manservice.entity.Category;
import vip.self.manservice.entity.User;
import vip.self.manservice.entity.vo.ArticleVo;
import vip.self.manservice.mapper.ArticleMapper;
import vip.self.manservice.other.util.MyUtil;
import vip.self.manservice.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.self.manservice.service.CategoryService;
import vip.self.manservice.service.MessageService;
import vip.self.manservice.service.UserService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageService messageService;

    /**
     *
     * @param title 文章标题
     * @param firstCategoryId 一级分类
     * @param secondCategoryId 二级分类
     * @param preDate 起始时间-修改
     * @param sufDate 结束时间-修改
     * @param level 文章级别 0正常 1推荐 2置顶
     * @param state 文章状态 0草稿 1审核中 2审核通过 3审核未通过
     * @param cur 当前页
     * @param size 每页数量
     * @return
     * @throws ParseException
     */
    @Override
    @SneakyThrows
    public Page<Article> getArticleList(String title,
                                        String firstCategoryId,
                                        String secondCategoryId,
                                        String preDate,
                                        String sufDate,
                                        Integer level,
                                        Integer state,
                                        Integer cur,
                                        Integer size) {
        Page<Article> page = new Page<>(cur, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.like(!MyUtil.strIsEmpty(title), "title", title);
        if (!MyUtil.strIsEmpty(firstCategoryId)) wrapper.eq("first_category_id", firstCategoryId);
        if (!MyUtil.strIsEmpty(secondCategoryId)) wrapper.eq("second_category_id", secondCategoryId);
        Date[] dates = MyUtil.formatPreSufDateStr(preDate, sufDate);
        if (dates[0] != null) wrapper.ge("modify_time", dates[0]);
        if (dates[1] != null) wrapper.le("modify_time", dates[1]);
        if (level == 0 || level == 1 || level == 2) wrapper.eq("level", level);
        MyUtil.queryState(state, wrapper);
        this.page(page, wrapper);
        return page;
    }

    /**
     * Article集合转ArticleVo集合
     * @param articles a集合
     * @return av集合
     */
    @Override
    public List<ArticleVo> a2av(List<Article> articles) {
        if (articles == null || articles.size() == 0) return null;
        List<ArticleVo> list = articles.stream().map(new Function<Article, ArticleVo>() {
            @Override
            public ArticleVo apply(Article a) {
                ArticleVo av = new ArticleVo();
                av.setId(a.getId());
                av.setTitle(a.getTitle());
                av.setCover(a.getCover());
                User user = userService.getById(a.getUserId());
                av.setUser(user);
                av.setWatch(a.getWatch());
                if (a.getFirstCategoryId() != null) {
                    Category first = categoryService.getById(a.getFirstCategoryId());
                    av.setFirstCategory(first);
                }
                if (a.getSecondCategoryId() != null) {
                    Category second = categoryService.getById(a.getSecondCategoryId());
                    av.setSecondCategory(second);
                }
                av.setLevel(String.valueOf(a.getLevel()));
                av.setModifyTime(a.getModifyTime());
                return av;
            }
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public User getUserById(String userId) {
        if (MyUtil.strIsEmpty(userId)) return null;
        User user = userService.getById(userId);
        return user;
    }

    @Override
    public Category getCategoryById(String cateId) {
        if (MyUtil.strIsEmpty(cateId)) return null;
        Category category = categoryService.getById(cateId);
        return category;
    }

    @Override
    public boolean setMessage(String... args) {
        return messageService.articlePassOrNo(args);
    }

    @Override
    public boolean delCateInArticle(String cateId, Integer level) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (level == 200001) queryWrapper.eq("first_category_id", cateId);
        else queryWrapper.eq("second_category_id", cateId);
        long count = this.count(queryWrapper);
        if (count == 0) return true;
        UpdateWrapper<Article> wrapper = new UpdateWrapper<>();
        if (level == 200001) { //一级
            wrapper.set("first_category_id", null);
            wrapper.eq("first_category_id", cateId);
        } else { // 二级
            wrapper.set("second_category_id", null);
            wrapper.eq("second_category_id", cateId);
        }
        boolean b = this.update(wrapper);
        if (!b) return false;
        return true;
    }
}
