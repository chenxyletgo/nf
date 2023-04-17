package vip.self.cliservice.service;

import vip.self.cliservice.entity.LikeA;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
public interface LikeAService extends IService<LikeA> {

    public abstract boolean userLikeArticle(String userId, String articleId);

    public abstract boolean addLikeA(String userId, String articleId);

}
