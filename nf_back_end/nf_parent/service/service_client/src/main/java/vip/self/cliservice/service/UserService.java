package vip.self.cliservice.service;

import vip.self.cliservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-09
 */
public interface UserService extends IService<User> {

    /**
     *
     * @param email
     * @return false不存在 true存在
     */
    public abstract boolean userIsExist(String email);

    /**
     *
     * @param response
     * @return 验证码
     */
    public abstract String writeCheckCodeImgToResponse(HttpServletResponse response);

    public abstract User getUserByEmail(String email);

    Long getFanCount(String userId);
    Long getFollowCount(String userId);

}
