package vip.self.uticommon.email;

import lombok.Data;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConditionalOnProperty(prefix = "myutil.email", name = "effective", havingValue = "true")
@ConfigurationProperties(prefix = "myutil.email")
public class EmailInfo {

    private String hostName;
    private String userName;
    private String password;

    /**
     *
     * @param targetEmail 目标邮箱地址
     * @param code 验证码
     * @param msg 发送的消息
     * @return 是否发送成功
     */
    public boolean sendSimpleTextEmail(String targetEmail, String code, String msg) {
        try {
            Email email = new SimpleEmail();
            //设置主机名,QQ邮箱是"smtp.qq.com",网易邮箱是"smtp.163.com"
            email.setHostName(this.hostName);
            // 用户名和密码为邮箱的账号和授权码（不需要进行base64编码）
            email.setAuthenticator(new DefaultAuthenticator(this.userName, this.password));
            //设置SSL连接,这样写就对了
            email.setSSLOnConnect(true);
            //设置来源,就是发送方的邮箱地址
            email.setFrom(this.userName);
            //设置主题,可以不设置
            email.setSubject("NF网站邮箱验证码");
            //设置信息,就是内容,这个必须要有
            email.setMsg(msg + code + "。");
            //接收人邮箱地址
            email.addTo(targetEmail);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
