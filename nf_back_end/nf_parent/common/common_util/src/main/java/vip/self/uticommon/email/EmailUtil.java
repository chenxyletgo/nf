package vip.self.uticommon.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

@Component
@ConditionalOnBean(EmailInfo.class)
public class EmailUtil {

    @Autowired
    private EmailInfo temp;
    private static String emailRegex = "^[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}$";
    private static EmailInfo email;
    private static Pattern pattern;

    @PostConstruct
    public void init() {
        email = temp;
        pattern = Pattern.compile(emailRegex);
    }

    /**
     * 注册时发送的邮箱
     * @param targetEmail 目标邮箱
     * @param code 验证码
     * @return 发送是否成功
     */
    public static boolean sendRegisterEmail(String targetEmail, String code) {
        return email.sendSimpleTextEmail(targetEmail, code, "您注册的邮箱验证码：");
    }

    /**
     * 找回密码时发送的邮箱
     * @param targetEmail 目标邮箱
     * @param code 验证码
     * @return 发送是否成功
     */
    public static boolean sendFindPwdEmail(String targetEmail, String code) {
        return email.sendSimpleTextEmail(targetEmail, code, "您找回的邮箱验证码：");
    }

    /**
     * 判断邮箱字符串是否合法
     * @param email 邮箱字符串
     * @return 邮箱字符串是否合法
     */
    public static boolean matcherEmail(String email) {
        return pattern.matcher(email).matches();
    }
}
