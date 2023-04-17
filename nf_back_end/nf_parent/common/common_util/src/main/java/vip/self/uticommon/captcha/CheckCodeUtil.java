package vip.self.uticommon.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
@ConditionalOnBean(CheckCodeInfo.class)
public class CheckCodeUtil {

    @Autowired
    private DefaultKaptcha temp;
    public static DefaultKaptcha kaptcha;

    @PostConstruct
    public void init() {
        kaptcha = temp;
    }

}
