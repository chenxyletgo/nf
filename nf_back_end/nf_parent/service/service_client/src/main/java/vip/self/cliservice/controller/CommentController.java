package vip.self.cliservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.cliservice.entity.Comment;
import vip.self.cliservice.entity.pro.CommentPro;
import vip.self.cliservice.entity.vo.CommentVo;
import vip.self.cliservice.other.util.MyUtil;
import vip.self.cliservice.service.CommentService;
import vip.self.uticommon.result.R;
import vip.self.uticommon.token.JwtUtil;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cld
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/cliservice/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService service;

    @PostMapping("/getCommentList/{cur}/{size}")
    public R getCommentList(@RequestBody CommentVo cv,
                            @RequestHeader(value = "token", required = false) String token,
                            @PathVariable(value = "cur") Long cur,
                            @PathVariable(value = "size") Long size) {
        String curUserId = null;
        boolean b = JwtUtil.checkToken(token);
        if (b && JwtUtil.getUserStateByJwtToken(token) != 2) {
            curUserId = JwtUtil.getUserIdByJwtToken(token);
        }
        Page<CommentPro> page = service.getCommentList(cv.getId(), cv.getLevel().intValue(), cv.getSort().intValue(), cur.intValue(), size.intValue());
        long total = page.getTotal();
        List<CommentPro> temp = page.getRecords();
        List<CommentVo> list = service.cps2cvs(temp, curUserId);
        return R.ok().data("list", list).data("total", total);
    }

    @PostMapping("/log/addComment")
    public R addComment(@RequestHeader(value = "token") String token,
                        @RequestBody CommentVo cv) {
        String curUserId = null;
        boolean b = JwtUtil.checkToken(token);
        if (b && JwtUtil.getUserStateByJwtToken(token) != 2) {
            curUserId = JwtUtil.getUserIdByJwtToken(token);
        }
        if (MyUtil.checkStrArr(cv.getId(), cv.getRuserId(), cv.getWords())) return R.error().setMessage("不能有空");
        Comment comment = new Comment();
        comment.setWords(cv.getWords());
        comment.setSUserId(curUserId);
        comment.setReplyId(cv.getId());
        if ("no1".equals(cv.getRuserId())) {
            comment.setReplyType(0);
        } else if ("no2".equals(cv.getRuserId())) {
            comment.setReplyType(1);
        } else {
            comment.setReplyType(1).setRUserId(cv.getRuserId());
        }
        boolean isOk = service.save(comment);
        if (!isOk) return R.error().setMessage("评论失败");
        return R.ok().setMessage("评论成功");
    }

    @GetMapping("/log/getCommListByArticleId/{id}/{cur}/{size}")
    public R getCommentListByArticleId(@PathVariable(value = "id") String id,
                                       @PathVariable(value = "cur") Long cur,
                                       @PathVariable(value = "size") Long size) {
        Page<Comment> page = service.getCommentListByArticle(id, cur.intValue(), size.intValue());
        long total = page.getTotal();
        List<Comment> temp = page.getRecords();
        List<CommentVo> list = service.cs2cvs(temp);
        return R.ok().data("list", list).data("total", total);
    }

    @DeleteMapping("/log/delComment")
    public R delComment(@RequestBody CommentVo cv) {
        if (MyUtil.strIsEmpty(cv.getId())) return R.error().setMessage("非法请求");
        Comment comm = service.getById(cv.getId());
        if (comm.getReplyType() == 0) {
            Long count = service.SonCommCountByFirstCommId(comm.getId());
            if (count != 0) return R.error().setMessage("需要先删除该评论下的子评论");
        }
        boolean b = service.removeById(comm.getId());
        if (!b) return R.error().setMessage("删除失败");
        return R.ok().setMessage("删除成功");
    }

}
