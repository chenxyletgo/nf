package vip.self.manservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.manservice.entity.Article;
import vip.self.manservice.entity.Category;
import vip.self.manservice.entity.User;
import vip.self.manservice.other.util.MyUtil;
import vip.self.manservice.service.ArticleService;
import vip.self.manservice.service.CategoryService;
import vip.self.manservice.service.UserService;
import vip.self.uticommon.encryption.MD5Vo;
import vip.self.uticommon.encryption.PasswordUtil;
import vip.self.uticommon.result.R;

@RestController
@RequestMapping("/manservice/test")
@CrossOrigin
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    /**
     * 添加管理员
     * @param user 必须要有的数据：email name password
     * @return 响应信息
     */
    @PostMapping("/user")
    public R addAdmin(@RequestBody User user) {
        boolean b1 = MyUtil.checkStrArr(user.getEmail(), user.getName(), user.getPassword());
        if (b1) return R.error().setMessage("参数不合法");
        String srcPwd = user.getPassword();
        MD5Vo pwdVo = PasswordUtil.encrypt(srcPwd);
        user.setSalt(pwdVo.getSalt());
        user.setPassword(pwdVo.getPassword());
        user.setState(3);
        boolean b2 = userService.save(user);
        if (!b2) return R.error().setMessage("管理员用户创建失败");
        return R.error().setMessage("管理员用户创建成功");
    }

//    /**
//     * 添加类别
//     * @param category 类别
//     * @return 响应
//     */
//    @PostMapping("/category")
//    public R addCategory(@RequestBody Category category) {
//        if (category.getPathName() != null) {
//            boolean b1 = MyUtil.matcherAlphabet(category.getPathName());
//            if (!b1) return R.error().setMessage("路径名不合法");
//        }
//        boolean b2 = categoryService.save(category);
//        if (!b2) return R.error().setMessage("类别创建失败");
//        return R.ok().setMessage("类别创建成功");
//    }

    /**
     * 标题和正文可用户id不能为空
     * 添加文章，并没有关心是草稿、发布等...
     * @param article 文章
     * @return 响应
     */
    @PostMapping("/article")
    public R addArticle(@RequestBody Article article) {
        if (MyUtil.strIsEmpty(article.getTitle())) return R.error().setMessage("标题不能为空");
        if (MyUtil.strIsEmpty(article.getText())) return R.error().setMessage("内容不能为空");
        if (article.getState() == -1) return R.error().setMessage("状态未选择");
        if (MyUtil.strIsEmpty(article.getFirstCategoryId())) article.setFirstCategoryId(null);
        if (MyUtil.strIsEmpty(article.getSecondCategoryId())) article.setSecondCategoryId(null);
        boolean b = articleService.save(article);
        if (!b) return R.error().setMessage("文章提交失败");
        return R.ok().setMessage("文章提交成功");
    }

}
