package vip.self.cliservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import vip.self.cliservice.entity.User;
import vip.self.cliservice.mapper.UserMapper;
import vip.self.cliservice.service.FanService;
import vip.self.cliservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.self.uticommon.captcha.CheckCodeUtil;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cld
 * @since 2023-04-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired(required = false)
    private UserMapper mapper;

    @Autowired
    private FanService fanService;

    @Override
    public boolean userIsExist(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        Long count = mapper.selectCount(wrapper);
        if (count == 0) return false;
        return true;
    }

    @Override
    public String writeCheckCodeImgToResponse(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        DefaultKaptcha kaptcha = CheckCodeUtil.kaptcha;
        String checkCode = kaptcha.createText();
        BufferedImage image = kaptcha.createImage(checkCode);
        try {
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            IOUtils.closeQuietly(out);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return checkCode;
    }

    @Override
    public User getUserByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        User user = this.getOne(wrapper);
        return user;
    }

    @Override
    public Long getFanCount(String userId) {
        return fanService.getFanCountByUserId(userId);
    }

    @Override
    public Long getFollowCount(String userId) {
        return fanService.getFollowCountByUserId(userId);
    }
}
