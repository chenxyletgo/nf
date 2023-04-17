package vip.self.uticommon.encryption;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtil {

    /**
     * 输入明文密码，返回加密后的密码和盐
     * @param src 明文密码
     * @return MD5Vo：6位salt；32位加盐MD5密码
     */
    public static MD5Vo encrypt(String src) {
        String salt = RandomStringUtils.randomAlphanumeric(6);
        String password = MD5.encrypt(salt + src);
        return new MD5Vo(salt, password);
    }

    /**
     * 判断密码是否正确
     * @param salt 盐
     * @param src 明文密码
     * @param password 加密的密码
     * @return 是否正确
     */
    public static boolean pwdIsOk(String salt, String src, String password) {
        return password.equals(MD5.encrypt(salt + src));
    }

}
