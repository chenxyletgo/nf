package vip.self.manservice.service;

import vip.self.manservice.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
public interface MessageService extends IService<Message> {


    /**
     * 只有两种情况
     *  情况一：两个参数 userId articleId
     *  情况二：三个参数 userId articleId msg
     *  情况一表示通过
     *  情况二表示不通过
     * @param args 字符串数组
     * @return 是否发送消息成功
     */
    public abstract boolean articlePassOrNo(String... args);

}
