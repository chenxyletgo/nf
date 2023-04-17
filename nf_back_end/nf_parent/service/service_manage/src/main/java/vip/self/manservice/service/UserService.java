package vip.self.manservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.self.manservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
public interface UserService extends IService<User> {

    /**
     * 用户搜索
     * @param name 用户名
     * @param sex 性别 0女 1男 2保密
     * @param preDate 起始日期
     * @param sufDate 结束日期
     * @param habitualResidence 居住地
     * @param state 账号水平
     * @param cur 当前页
     * @param size 每页数量
     * @return 分页对象
     */
    public abstract Page<User> getUseList(String name,
                                          Integer sex,
                                          String preDate,
                                          String sufDate,
                                          String habitualResidence,
                                          Integer state,
                                          Integer cur,
                                          Integer size);

    /**
     * 逻辑删除用户
     * @param id 用户id
     * @return 成功true 失败false
     */
    public abstract boolean delUser(String id);
}
