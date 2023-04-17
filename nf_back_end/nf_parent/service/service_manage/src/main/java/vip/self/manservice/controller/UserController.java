package vip.self.manservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.manservice.entity.User;
import vip.self.manservice.entity.vo.UserVo;
import vip.self.manservice.other.util.MyUtil;
import vip.self.manservice.service.UserService;
import vip.self.uticommon.encryption.PasswordUtil;
import vip.self.uticommon.result.R;
import vip.self.uticommon.token.JwtUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
@RestController
@RequestMapping("/manservice/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    /**
     * 管理员登录
     * @param user 必须要有的数据 email password
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        boolean b1 = MyUtil.checkStrArr(user.getEmail(), user.getPassword());
        if (b1) return R.error().setMessage("参数不合法");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", user.getEmail());
        User target = service.getOne(wrapper);
        if (target == null) return R.error().setMessage("不存在该账号");
        Integer level = target.getState();
        if (level != 3) return R.error().setMessage("此账号没有管理权限");
        boolean b2 = PasswordUtil.pwdIsOk(target.getSalt(), user.getPassword(), target.getPassword());
        if (!b2) return R.error().setMessage("密码错误");
        String token = JwtUtil.getJwtToken(target.getId(),
                target.getName(),
                String.valueOf(target.getState()));
        return R.ok().data("token", token).setMessage("登录成功");
    }

    /**
     * 搜索用户列表
     * @param uv 搜索条件
     * @param cur 当前页
     * @param size 每页个数
     * @return 响应
     */
    @PostMapping("/adm/getUserList/{cur}/{size}")
    public R getUserList(@RequestBody UserVo uv,
                         @PathVariable(value = "cur") Long cur,
                         @PathVariable(value = "size") Long size) {
        if (MyUtil.strIsEmpty(uv.getSex())) uv.setSex("-1");
        if (MyUtil.strIsEmpty(uv.getState())) uv.setState("-1");
        Page<User> page = service.getUseList(
                uv.getName(),
                Integer.valueOf(uv.getSex()),
                uv.getPreDate(),
                uv.getSufDate(),
                uv.getHabitualResidence(),
                Integer.valueOf(uv.getState()),
                cur.intValue(),
                size.intValue());
        return R.ok().data("list", page.getRecords()).data("total", page.getTotal());
    }

    /**
     * 前端传递一个用户id，一个msg，根据msg判断是禁言还是封禁...
     * @return 响应
     */
    @PutMapping("/adm/setUserState/{msg}")
    public R setUserState(@RequestBody User user,
                          @PathVariable(value = "msg") Long msg) {
        switch (msg.intValue()) {
            case 0: user.setState(0); break;
            case 1: user.setState(1); break;
            case 2: user.setState(2); break;
            case 3: user.setState(3); break;
            default: return R.error().setMessage("非法请求");
        }
        boolean b = service.updateById(user);
        if (!b) return R.error().setMessage("修改失败");
        return R.ok().setMessage("修改成功");
    }

    /**
     * 删除用户
     * @param user 要删除的用户
     * @return 响应
     */
    @DeleteMapping("/adm")
    public R delUser(@RequestBody User user) {
        boolean b = service.delUser(user.getId());
        if (!b) return R.error().setMessage("删除失败");
        return R.ok().setMessage("删除成功");
    }
}
