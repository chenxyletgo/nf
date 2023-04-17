package vip.self.manservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import vip.self.manservice.entity.User;
import vip.self.manservice.mapper.UserMapper;
import vip.self.manservice.other.util.MyUtil;
import vip.self.manservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    /**
     *
     * @param name 用户名
     * @param sex 性别 0女 1男 2保密
     * @param preAge 起始日期
     * @param sufAge 结束日期
     * @param habitualResidence 居住地
     * @param state 账号水平
     * @param cur 当前页
     * @param size 每页数量
     * @return 分页对象
     */
    @Override
    @SneakyThrows
    public Page<User> getUseList(String name,
                                 Integer sex,
                                 String preAge,
                                 String sufAge,
                                 String habitualResidence,
                                 Integer state,
                                 Integer cur,
                                 Integer size) {
        Page<User> page = new Page<>(cur, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(!MyUtil.strIsEmpty(name), "name", name);
        if (sex == 0 || sex == 1 || sex == 2) wrapper.eq("sex", sex);
        Date[] dates = MyUtil.formatPreSufDateStr(preAge, sufAge);
        if (dates[0] != null) wrapper.ge("birthday", dates[0]);
        if (dates[1] != null) wrapper.le("birthday", dates[1]);
        if (!MyUtil.strIsEmpty(habitualResidence)) wrapper.eq("habitual_residence", habitualResidence);
        MyUtil.queryState(state, wrapper);
        this.page(page, wrapper);
        return page;
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 是否成功
     */
    @Override
    public boolean delUser(String id) {
        User user = this.getById(id);
        if (user == null) return false;
        user.setDelUniqueKey(user.getId());
        boolean b1 = this.updateById(user);
        if (!b1) return false;
        boolean b2 = this.removeById(user);
        if (!b2) return false;
        return true;
    }
}
