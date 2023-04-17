package vip.self.cliservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import vip.self.cliservice.entity.LikeC;
import vip.self.cliservice.mapper.LikeCMapper;
import vip.self.cliservice.other.util.MyUtil;
import vip.self.cliservice.service.LikeCService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
@Service
public class LikeCServiceImpl extends ServiceImpl<LikeCMapper, LikeC> implements LikeCService {

    @Override
    public Long getLikeCountByCommentId(String id) {
        QueryWrapper<LikeC> wrapper = new QueryWrapper<>();
        wrapper.eq("attitude", 1);
        wrapper.eq("comment_id", id);
        long count = this.count(wrapper);
        return count;
    }

    @Override
    public Long getDislikeCountByCommentId(String id) {
        QueryWrapper<LikeC> wrapper = new QueryWrapper<>();
        wrapper.eq("attitude", 0);
        wrapper.eq("comment_id", id);
        long count = this.count(wrapper);
        return count;
    }

    @Override
    public Integer curUserIsComm(String curUserId, String commId) {
        if (MyUtil.strIsEmpty(curUserId)) return -1;
        QueryWrapper<LikeC> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", commId);
        wrapper.eq("user_id", curUserId);
        LikeC one = this.getOne(wrapper);
        if (one == null) return -1;
        return one.getAttitude();
    }
}
