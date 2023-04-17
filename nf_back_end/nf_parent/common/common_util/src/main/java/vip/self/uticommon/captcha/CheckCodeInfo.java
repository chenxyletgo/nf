package vip.self.uticommon.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.Properties;

/**
 * 验证码
 */
@Data
@Component
@ConditionalOnProperty(prefix = "myutil.captcha", name = "effective", havingValue = "true")
@ConfigurationProperties(prefix = "myutil.captcha")
public class CheckCodeInfo {

    // 验证码属性
    private String border;
    private String color;
    private String space;

    @Bean
    public DefaultKaptcha makeImg() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", this.border);
        properties.put("kaptcha.textproducer.font.color", this.color);
        properties.put("kaptcha.textproducer.char.space", this.space);
        Config config = new Config(properties);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
