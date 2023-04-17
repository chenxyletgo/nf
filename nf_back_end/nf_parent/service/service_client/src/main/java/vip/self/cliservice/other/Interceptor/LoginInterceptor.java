package vip.self.cliservice.other.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import vip.self.concommon.exception.NFException;
import vip.self.uticommon.token.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean b = JwtUtil.checkToken(request);
        Integer state = JwtUtil.getUserStateByJwtToken(request);
        if (!b || state == null) throw new NFException(20001, "未登录");
        if (state == 2) throw new NFException(20001, "该账号已被封禁");
        return true;
    }
}
