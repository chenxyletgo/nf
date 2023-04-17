package vip.self.cliservice.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.self.cliservice.entity.User;
import vip.self.cliservice.entity.vo.UserVo;
import vip.self.cliservice.other.util.MyUtil;
import vip.self.cliservice.service.UserService;
import vip.self.uticommon.email.EmailUtil;
import vip.self.uticommon.encryption.MD5Vo;
import vip.self.uticommon.encryption.PasswordUtil;
import vip.self.uticommon.result.R;
import vip.self.uticommon.token.JwtUtil;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cld
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/cliservice/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public R register(@RequestBody UserVo uv, HttpSession session) {
        boolean b1 = MyUtil.checkStrArr(uv.getName(), uv.getEmail(), uv.getPassword(), uv.getEmailCode());
        if (b1) return R.error().setMessage("数据不能有空");
        String emailCode = (String)session.getAttribute("emailCode");
        Object email = session.getAttribute("email");
        if (emailCode == null) return R.error().setMessage("邮箱验证码已过期");
        if (!emailCode.equals(uv.getEmailCode())) return R.error().setMessage("邮箱验证码错误");
        if (email == null) return R.error().setMessage("需要邮箱");
        if (!email.equals(uv.getEmail())) return R.error().setMessage("邮箱与邮箱验证码不匹配");
        boolean b2 = EmailUtil.matcherEmail(uv.getEmail());
        if (!b2) return R.error().setMessage("邮箱格式不正确");
        boolean b3 = service.userIsExist(uv.getEmail());
        if (b3) return R.error().setMessage("该邮箱已经被注册了");
        session.removeAttribute("emailCode");
        session.removeAttribute("email");
        User user = new User();
        user.setName(uv.getName());
        user.setEmail(uv.getEmail());
        MD5Vo md5Vo = PasswordUtil.encrypt(uv.getPassword());
        user.setSalt(md5Vo.getSalt());
        user.setPassword(md5Vo.getPassword());
        boolean b4 = service.save(user);
        if (!b4) return R.error().setMessage("注册失败");
        return R.ok().setMessage("注册成功");
    }

    @PostMapping("/sendEmailCode")
    public R sendEmailCode(@RequestBody UserVo uv, HttpSession session) {
        if (MyUtil.strIsEmpty(uv.getEmail())) return R.error().setMessage("邮箱不能为空");
        boolean b1 = EmailUtil.matcherEmail(uv.getEmail());
        if (!b1) return R.error().setMessage("邮箱格式不正确");
        boolean b2 = service.userIsExist(uv.getEmail());
        if (b2) return R.error().setMessage("邮箱已经被注册了");
        String emailCode = RandomStringUtils.randomNumeric(6);
        boolean b3 = EmailUtil.sendRegisterEmail(uv.getEmail(), emailCode);
        if (!b3) return R.error().setMessage("邮箱验证码发送失败");
        session.setAttribute("emailCode", emailCode);
        session.setAttribute("email", uv.getEmail());
        return R.ok().setMessage("邮箱验证码已发送");
    }

    @PostMapping("/login")
    public R login(@RequestBody UserVo uv, HttpSession session) {
        boolean b1 = MyUtil.checkStrArr(uv.getEmail(), uv.getPassword(), uv.getCheckCode());
        if (b1) return R.error().setMessage("数据不能为空");
        String checkCode = (String)session.getAttribute("checkCode");
        if (checkCode == null) return R.error().setMessage("验证码已过期");
        if (!checkCode.equals(uv.getCheckCode())) return R.error().setMessage("验证码错误");
        User user = service.getUserByEmail(uv.getEmail());
        if (user == null) return R.error().setMessage("该用户未注册");
        boolean b2 = PasswordUtil.pwdIsOk(user.getSalt(), uv.getPassword(), user.getPassword());
        if (!b2) return R.error().setMessage("密码错误");
        if (user.getState() == 2) return R.error().setMessage("该用户被封禁了");
        String token = JwtUtil.getJwtToken(user.getId(), user.getName(), String.valueOf(user.getState()));
        return R.ok().data("token", token).data("name", user.getName()).data("avatar", user.getAvatar()).setMessage("登录成功");
    }

    @GetMapping("/getCheckCode")
    public void getCheckCodeImg(HttpServletResponse response, HttpSession session) {
        String checkCode = service.writeCheckCodeImgToResponse(response);
        if (checkCode != null) session.setAttribute("checkCode", checkCode);
    }

    @GetMapping("/log/getUserInfo")
    public R getUserInfo(@RequestHeader(value = "token") String token) {
        String userId = JwtUtil.getUserIdByJwtToken(token);
        User user = service.getById(userId);
        UserVo uv = new UserVo();
        uv.setId(user.getId());
        uv.setName(user.getName());
        uv.setAvatar(user.getAvatar());
        uv.setMessage(user.getMessage());
        uv.setFan(service.getFanCount(user.getId()));
        uv.setFollow(service.getFollowCount(user.getId()));
        uv.setSex(user.getSex());
        uv.setHabitualResidence(user.getHabitualResidence());
        uv.setBirthday(MyUtil.date2Str(user.getBirthday()));
        return R.ok().data("user", uv);
    }

    @PutMapping("/log/editUserInfo")
    public R editUserInfo(@RequestHeader(value = "token") String token,
                          @RequestBody UserVo uv) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        if (MyUtil.strIsEmpty(uv.getName())) return R.error().setMessage("昵称不能未空");
        wrapper.set("name", uv.getName());
        wrapper.set("avatar", MyUtil.strIsEmpty(uv.getAvatar()) ? null : uv.getAvatar());
        wrapper.set("message", MyUtil.strIsEmpty(uv.getMessage()) ? null : uv.getMessage());
        wrapper.set("sex", uv.getSex());
        wrapper.set("habitual_residence", MyUtil.strIsEmpty(uv.getHabitualResidence()) ? null : uv.getHabitualResidence());
        Date birthday = null;
        try {
            birthday = MyUtil.formatDateStr(uv.getBirthday());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        wrapper.set("birthday", birthday == null ? null : birthday);
        wrapper.eq("id", JwtUtil.getUserIdByJwtToken(token));
        boolean b = service.update(wrapper);
        if (!b) return R.error().setMessage("修改失败");
        return R.ok().setMessage("修改成功");
    }

    @GetMapping("/getUserState")
    public R getUserState(@RequestHeader(value = "token", required = false) String token) {
        if (MyUtil.strIsEmpty(token)) return R.warn().setMessage("未登录");
        if (!JwtUtil.checkToken(token)) return R.error().setMessage("登录过期");
        User user = service.getById(JwtUtil.getUserIdByJwtToken(token));
        UserVo uv = new UserVo();
        uv.setName(user.getName());
        uv.setAvatar(user.getAvatar());
        return R.ok().data("user", uv);
    }

}
