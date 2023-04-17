package vip.self.cliservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.cliservice.entity.Message;
import vip.self.cliservice.service.MessageService;
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
@RequestMapping("/cliservice/message")
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageService service;

    @GetMapping("/log/getMessList/{cur}/{size}")
    public R getMessList(@RequestHeader(value = "token") String token,
                         @PathVariable(value = "cur") Long cur,
                         @PathVariable(value = "size") Long size) {
        String userId = JwtUtil.getUserIdByJwtToken(token);
        Page<Message> page = service.getMessList(userId, cur.intValue(), size.intValue());
        long total = page.getTotal();
        List<Message> list = page.getRecords();
        return R.ok().data("list", list).data("total", total);
    }

}
