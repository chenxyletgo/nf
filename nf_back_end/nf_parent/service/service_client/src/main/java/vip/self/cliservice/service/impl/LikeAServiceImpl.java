package vip.self.cliservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import vip.self.cliservice.entity.LikeA;
import vip.self.cliservice.mapper.LikeAMapper;
import vip.self.cliservice.other.util.MyUtil;
import vip.self.cliservice.service.LikeAService;
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
public class LikeAServiceImpl extends ServiceImpl<LikeAMapper, LikeA> implements LikeAService {

    @Override
    public boolean userLikeArticle(String userId, String articleId) {
        if (MyUtil.strIsEmpty(userId)) return false;
        QueryWrapper<LikeA> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        wrapper.eq("user_id", userId);
        long count = this.count(wrapper);
        if (count == 0) return false;
        return true;
    }

    @Override
    public boolean addLikeA(String userId, String articleId) {
        QueryWrapper<LikeA> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        wrapper.eq("user_id", userId);
        LikeA one = this.getOne(wrapper);
        if (one != null) { // 已经喜欢了
            boolean b = this.removeById(one.getId());
            if (!b) return false;
            return true;
        }
        // 没有喜欢
        LikeA likeA = new LikeA();
        likeA.setArticleId(articleId);
        likeA.setUserId(userId);
        boolean b = this.save(likeA);
        if (!b) return false;
        return true;

    }
}
