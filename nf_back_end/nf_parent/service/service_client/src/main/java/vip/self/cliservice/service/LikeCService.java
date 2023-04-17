package vip.self.cliservice.service;

import vip.self.cliservice.entity.LikeC;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
public interface LikeCService extends IService<LikeC> {

    Long getLikeCountByCommentId(String id);
    Long getDislikeCountByCommentId(String id);

    /**
     *
     * @param curUserId
     * @param commId
     * @return -1代表未评价 0不喜欢 1喜欢
     */
    Integer curUserIsComm(String curUserId, String commId);

}
