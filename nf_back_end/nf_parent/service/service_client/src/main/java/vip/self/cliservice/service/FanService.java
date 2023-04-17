package vip.self.cliservice.service;

import vip.self.cliservice.entity.Fan;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
public interface FanService extends IService<Fan> {

    public abstract Long getFanCountByUserId(String userId);

    public abstract Long getFollowCountByUserId(String userId);

    public abstract boolean isFan(String bloggerId, String curUserId);

}
