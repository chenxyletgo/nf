package vip.self.cliservice.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.cliservice.entity.Article;
import vip.self.cliservice.entity.pro.ArticlePro;
import vip.self.cliservice.entity.vo.ArticleVo;
import vip.self.cliservice.other.util.MyUtil;
import vip.self.cliservice.service.ArticleService;
import vip.self.uticommon.result.R;
import vip.self.uticommon.token.JwtUtil;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
@RestController
@RequestMapping("/cliservice/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @PostMapping("/getArticleDivList/{cur}/{size}")
    public R getArticleDivList(@RequestHeader(value = "token", required = false) String token,
                               @RequestBody ArticleVo av,
                               @PathVariable(value = "cur") Long cur,
                               @PathVariable(value = "size") Long size) {
        String curUserId = null;
        boolean b = JwtUtil.checkToken(token);
        if (b && JwtUtil.getUserStateByJwtToken(token) != 2) {
            curUserId = JwtUtil.getUserIdByJwtToken(token);
        }

        Page<ArticlePro> page = service.getArticleList(null,
                av.getPathName(),
                av.getSecondCateId(),
                2,
                av.getSort().intValue(),
                cur.intValue(),
                size.intValue());
        List<ArticlePro> temp = page.getRecords();
        long total = page.getTotal();
        List<ArticleVo> list = service.aps2avs(temp, curUserId);
        return R.ok().data("list", list).data("total", total);
    }

    @GetMapping("/getArticle/{state}/{id}")
    public R getArticle(@RequestHeader(value = "token", required = false) String token,
                        @PathVariable(value = "state") Long state,
                        @PathVariable(value = "id") String id) {

        if (state.intValue() != -1 && state.intValue() != 2) return R.error().setMessage("非法请求");
        String curUserId = null;
        boolean b = JwtUtil.checkToken(token);
        if (b && JwtUtil.getUserStateByJwtToken(token) != 2) {
            curUserId = JwtUtil.getUserIdByJwtToken(token);
        }
        if (state.intValue() == 2) {
            UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.setSql("watch = watch + 1");
            service.update(updateWrapper);
        }
        ArticlePro ap = service.getArticleProById(id, state.intValue());
        if (state.intValue() == -1 && !ap.getUserId().equals(curUserId)) return R.error().setMessage("不是你的文章");
        ArticleVo av = service.ap2av(ap, curUserId);
        return R.ok().data("article", av);
    }

    /**
     *
     * @param token
     * @param av
     * @param action 0表示发布 1表示草稿
     * @return
     */
    @PostMapping("/log/postArticle/{action}")
    public R setState1(@RequestHeader(value = "token") String token,
                       @RequestBody ArticleVo av,
                       @PathVariable(value = "action") Long action) {
        if (action != 0 && action != 1) return R.error().setMessage("非法请求");
        if (MyUtil.checkStrArr(av.getTitle(), av.getText())) return R.error().setMessage("缺少必要信息");
        String userId = JwtUtil.getUserIdByJwtToken(token);
        Article article = new Article();
        article.setTitle(av.getTitle());
        article.setText(av.getText());
        if (!MyUtil.strIsEmpty(av.getFirstCateId())) article.setFirstCategoryId(av.getFirstCateId());
        if (!MyUtil.strIsEmpty(av.getSecondCateId())) article.setSecondCategoryId(av.getSecondCateId());
        if (!MyUtil.strIsEmpty(av.getCover())) article.setCover(av.getCover());
        if (action == 0) article.setState(1);
        if (action == 1) article.setState(0);
        if (MyUtil.strIsEmpty(av.getId())) {
            article.setUserId(userId);
            boolean b1 = service.save(article);
            if (!b1) return R.error().setMessage("操作失败");
            return R.ok().setMessage("操作成功");
        }
        article.setId(av.getId());
        boolean b2 = service.updateById(article);
        if (!b2) return R.error().setMessage("操作失败");
        return R.ok().setMessage("操作成功");

    }

    /**
     * state -1全部 0 1 2 3对应
     * @return
     */
    @GetMapping("/log/getArticleListByUserId/{state}/{cur}/{size}")
    public R getArticleListByUserId(@RequestHeader(value = "token") String token,
                                @PathVariable(value = "state") Long state,
                                @PathVariable(value = "cur") Long cur,
                                @PathVariable(value = "size") Long size) {
        String userId = JwtUtil.getUserIdByJwtToken(token);
        Page<ArticlePro> page = service.getArticleList(userId, state.intValue(), cur.intValue(), size.intValue());
        long total = page.getTotal();
        List<ArticlePro> temp = page.getRecords();
        List<ArticleVo> list = service.aps2avs(temp, userId);
        return R.ok().data("list", list).data("total", total);
    }

    @DeleteMapping("/log/delArticleById")
    public R delArticleById(@RequestHeader(value = "token") String token,
                            @RequestBody ArticleVo av) {
        if (MyUtil.strIsEmpty(av.getId())) return R.error().setMessage("非法请求");
        String userId = JwtUtil.getUserIdByJwtToken(token);
        Article article = service.getById(av.getId());
        if (article == null) return R.error().setMessage("没有该文章");
        if (!article.getUserId().equals(userId)) return R.error().setMessage("非法请求");
        boolean b = service.removeById(article.getId());
        if (!b) return R.error().setMessage("删除失败");
        return R.ok().setMessage("删除成功");
    }

    @GetMapping("/log/getAllArticleByUseId")
    public R getAllArticleByUserId(@RequestHeader(value = "token") String token) {
        String userId = JwtUtil.getUserIdByJwtToken(token);
        List<Article> list = service.getAllArticleByUserId(userId);
        return R.ok().data("list", list);
    }

    @GetMapping("/getTopArticleList")
    public R getTopArticleList() {
        List<Article> list = service.getTopArticleList();
        return R.ok().data("list", list);
    }

    @PostMapping("/search/{sort}/{cur}/{size}")
    public R getArticleDivListByTitle(@RequestHeader(value = "token", required = false) String token,
                                      @RequestBody ArticleVo av,
                                      @PathVariable(value = "sort") Long sort,
                                      @PathVariable(value = "cur") Long cur,
                                      @PathVariable(value = "size") Long size) {
        String userId = null;
        if (token != null && JwtUtil.checkToken(token)) userId = JwtUtil.getUserIdByJwtToken(token);
        Page<ArticlePro> page = service.getArticleList(av.getTitle(),
                null,
                null,
                2,
                sort.intValue(),
                cur.intValue(),
                size.intValue()
        );
        long total = page.getTotal();
        List<ArticlePro> temp = page.getRecords();
        List<ArticleVo> list = service.aps2avs(temp, userId);
        return R.ok().data("list", list).data("total", total);
    }

    @GetMapping("/log/preview/{id}")
    public R preview(@RequestHeader(value = "token") String token,
                     @PathVariable(value = "id") String id) {
        String userId = JwtUtil.getUserIdByJwtToken(token);
        if (MyUtil.strIsEmpty(id)) return R.error().setMessage("非法请求");
        ArticlePro ap = service.getArticleProById(id, -1);
        if (!ap.getUserId().equals(userId)) return R.error().setMessage("非法请求");
        ArticleVo av = service.ap2av(ap, userId);
        return R.ok().data("article", av);
    }
}
