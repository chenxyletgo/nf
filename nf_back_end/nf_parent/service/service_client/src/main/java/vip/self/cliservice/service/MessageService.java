package vip.self.cliservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.self.cliservice.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2023-04-10
 */
public interface MessageService extends IService<Message> {

    public abstract Page<Message> getMessList(String userId, Integer cur, Integer size);

}
