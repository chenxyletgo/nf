package vip.self.manservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.manservice.entity.Article;
import vip.self.manservice.entity.Category;
import vip.self.manservice.entity.User;
import vip.self.manservice.entity.vo.ArticleVo;
import vip.self.manservice.other.util.MyUtil;
import vip.self.manservice.service.ArticleService;
import vip.self.uticommon.result.R;
import vip.self.uticommon.token.JwtUtil;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
@RestController
@RequestMapping("/manservice/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService service;

    @PostMapping("/adm/getArticleList/{cur}/{size}")
    public R getArticleList(
            @RequestBody ArticleVo av,
            @PathVariable(value = "cur") Long cur,
            @PathVariable(value = "size") Long size) {
        if (MyUtil.strIsEmpty(av.getLevel())) av.setLevel("-1");
        if (MyUtil.strIsEmpty(av.getState())) av.setState("-1");
        Page<Article> page = service.getArticleList(
                av.getTitle(),
                av.getFirstCategoryId(),
                av.getSecondCategoryId(),
                av.getPreDate(),
                av.getSufDate(),
                Integer.valueOf(av.getLevel()),
                Integer.valueOf(av.getState()),
                cur.intValue(),
                size.intValue());
        Long total = page.getTotal();
        List<Article> temp = page.getRecords();
        List<ArticleVo> list = service.a2av(temp);
        return R.ok().data("list", list).data("total", total);
    }

    @DeleteMapping("/adm")
    public R delArticle(@RequestBody Article article) {
        if (MyUtil.strIsEmpty(article.getId())) return R.error().setMessage("删除文章失败，提交的文章不合法");
        Article a = service.getById(article.getId());
        if (a == null) return R.error().setMessage("没有该文章，无法删除");
        boolean b = service.removeById(a.getId());
        if (!b) return R.error().setMessage("删除失败");
        return R.ok().setMessage("删除成功");
    }

    @PutMapping("/adm/setArticleLevel/{msg}")
    public R setArticleLevel(@RequestBody Article article, @PathVariable(value = "msg") Long msg) {
        switch (msg.intValue()) {
            case 0: article.setLevel(0); break;
            case 1: article.setLevel(1); break;
            case 2: article.setLevel(2); break;
            default: return R.error().setMessage("非法请求");
        }
        boolean b = service.updateById(article);
        if (!b) return R.error().setMessage("修改失败");
        return R.ok().setMessage("修改成功");
    }


    /**
     * av两种情况
     *  情况一：必有id
     *  情况二：必有id 必有msg
     * @param av
     * @param msg
     * @return
     */
    @PutMapping("/adm/setArticleState/{msg}")
    public R setArticleState(
            @RequestBody ArticleVo av,
            @PathVariable(value = "msg") Long msg) {
        if (MyUtil.strIsEmpty(av.getId())) return R.error().setMessage("非法请求");
        Article article = service.getById(av.getId());
        switch (msg.intValue()) {
            case 2: {
                article.setState(2);
                boolean b2 = service.setMessage(article.getUserId(), article.getId());
                if (!b2) return R.error().setMessage("发送消息失败");
                break;
            }
            case 3: {
                article.setState(3);
                boolean b3 = service.setMessage(article.getUserId(), article.getId(), av.getReason());
                if (!b3) return R.error().setMessage("发送消息失败，原因不能为空");
                break;
            }
            default: return R.error().setMessage("非法请求");
        }
        boolean b = service.updateById(article);
        if (!b) return R.error().setMessage("修改失败");
        return R.ok().setMessage("修改成功");
    }

    @GetMapping("/adm/{id}")
    public R getArticleById(@PathVariable(value = "id") String id) {
        if (MyUtil.strIsEmpty(id)) return R.error().setMessage("非法请求");
        Article article = service.getById(id);
        if (article == null) return R.error().setMessage("获取文章失败");
        ArticleVo av = new ArticleVo();
        av.setTitle(article.getTitle());
        av.setText(article.getText());
        User user = service.getUserById(article.getUserId());
        av.setUser(user);
        Category category = service.getCategoryById(article.getFirstCategoryId());
        av.setFirstCategory(category);
        return R.ok().data("article", av);
    }

}
