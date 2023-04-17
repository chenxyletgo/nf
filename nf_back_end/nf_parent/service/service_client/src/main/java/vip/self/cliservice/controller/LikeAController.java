package vip.self.cliservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.cliservice.entity.vo.ArticleVo;
import vip.self.cliservice.other.util.MyUtil;
import vip.self.cliservice.service.LikeAService;
import vip.self.uticommon.result.R;
import vip.self.uticommon.token.JwtUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
@RestController
@RequestMapping("/cliservice/likeA")
@CrossOrigin
public class LikeAController {

    @Autowired
    private LikeAService service;

    /**
     *
     * @param token
     * @param av 只是为了传递文章id
     * @return
     */
    @PutMapping("/log/addLikeA")
    public R addLikeA(@RequestHeader(value = "token") String token,
                      @RequestBody ArticleVo av) {
        String userId = JwtUtil.getUserIdByJwtToken(token);
        if (MyUtil.strIsEmpty(av.getId())) return R.error().setMessage("操作失败");
        boolean b = service.addLikeA(userId, av.getId());
        if (!b) return R.error().setMessage("操作失败");
        return R.ok().setMessage("操作成功");
    }

}
