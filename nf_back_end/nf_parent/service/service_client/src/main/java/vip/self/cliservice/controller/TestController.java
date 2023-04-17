package vip.self.cliservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.cliservice.entity.*;
import vip.self.cliservice.entity.pro.ArticlePro;
import vip.self.cliservice.entity.vo.TestVo;
import vip.self.cliservice.mapper.ArticleMapper;
import vip.self.cliservice.service.*;
import vip.self.uticommon.result.R;

import java.util.List;

@RestController
@RequestMapping("/cliservice/test")
@CrossOrigin
public class TestController {

    @Autowired
    private LikeAService likeAService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired(required = false)
    private ArticleMapper articleMapper;
    @Autowired
    private LikeCService likeCService;

    @PostMapping("/addLikeA")
    public R addLikeA(@RequestBody TestVo tv) {
        QueryWrapper<LikeA> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", tv.getArticleId());
        wrapper.eq("user_id", tv.getUserId());
        List<LikeA> list = likeAService.list(wrapper);
        if (list.size() >= 1) {
            return R.error().setMessage("你已经喜欢了");
        }
        LikeA likeA = new LikeA();
        likeA.setUserId(tv.getUserId());
        likeA.setArticleId(tv.getArticleId());
        boolean b = likeAService.save(likeA);
        if (!b) return R.error().setMessage("喜欢失败");
        return R.ok().setMessage("成功");
    }

    @GetMapping("/getAllArticle")
    public R getAllArticle() {
        List<Article> list = articleService.list();
        return R.ok().data("list", list);
    }

    @GetMapping("/getAllUser")
    public R getAllUser(){
        List<User> list = userService.list();
        return R.ok().data("list", list);
    }

    @PostMapping("/addComment")
    public R addComment(@RequestBody TestVo tv) {
        Comment comment = new Comment();
        comment.setWords(tv.getWords());
        comment.setReplyType(0);
        comment.setReplyId(tv.getArticleId());
        comment.setSUserId(tv.getUserId());
        boolean b = commentService.save(comment);
        if (!b) return R.error().setMessage("评论添加失败");
        return R.ok().setMessage("评论添加成功");
    }

    @GetMapping("/test01")
    public R test01() {
        Page<ArticlePro> page = new Page<>(1, 2);
        QueryWrapper<ArticlePro> wrapper = new QueryWrapper<>();
        wrapper.eq("main_temp.`like`", 1);
        articleMapper.selectArticlePro(page, wrapper);
        List<ArticlePro> list = page.getRecords();
        for (ArticlePro ap: list) {
            System.out.println(ap);
        }
        return R.ok().data("list", list);
    }


    @PostMapping("/addLikeC")
    public R addLikeC(@RequestBody LikeC lc) {
        boolean b = likeCService.save(lc);
        if (!b) return R.error().setMessage("保存失败");
        return R.ok().setMessage("保存成功");
    }
}
