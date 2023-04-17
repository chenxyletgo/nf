package vip.self.manservice.service.impl;

import vip.self.manservice.entity.Message;
import vip.self.manservice.mapper.MessageMapper;
import vip.self.manservice.other.util.MyUtil;
import vip.self.manservice.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cld
 * @since 2023-04-06
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    /**
     * 只有两种情况
     *  情况一：两个参数 userId articleId
     *  情况二：三个参数 userId articleId msg
     *  情况一表示通过
     *  情况二表示不通过
     * @param args 字符串数组
     * @return 是否发送消息成功
     */
    @Override
    public boolean articlePassOrNo(String... args) {
        if (args.length != 2 && args.length != 3) return false;
        boolean b1 = MyUtil.checkStrArr(args);
        if (b1) return false;
        String text = null;
        if (args.length == 2) text = MyUtil.jointMessageText(args[1]);
        if (args.length == 3) text = MyUtil.jointMessageText(args[1], args[2]);
        Message message = new Message();
        message.setUserId(args[0]);
        message.setText(text);
        boolean b2 = this.save(message);
        if (!b2) return false;
        return true;
    }
}
