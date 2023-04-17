package vip.self.cliservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import vip.self.cliservice.entity.Fan;
import vip.self.cliservice.mapper.FanMapper;
import vip.self.cliservice.other.util.MyUtil;
import vip.self.cliservice.service.FanService;
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
public class FanServiceImpl extends ServiceImpl<FanMapper, Fan> implements FanService {

    @Override
    public Long getFanCountByUserId(String userId) {
        QueryWrapper<Fan> wrapper = new QueryWrapper<>();
        wrapper.eq("b_user_id", userId);
        long count = this.count(wrapper);
        return count;
    }

    @Override
    public Long getFollowCountByUserId(String userId) {
        QueryWrapper<Fan> wrapper = new QueryWrapper<>();
        wrapper.eq("f_user_id", userId);
        long count = this.count(wrapper);
        return count;
    }

    @Override
    public boolean isFan(String bloggerId, String curUserId) {
        if (MyUtil.strIsEmpty(curUserId)) return false;
        QueryWrapper<Fan> wrapper = new QueryWrapper<>();
        wrapper.eq("b_user_id", bloggerId);
        wrapper.eq("f_user_id", curUserId);
        long count = this.count(wrapper);
        if (count == 0) return false;
        return true;
    }
}
