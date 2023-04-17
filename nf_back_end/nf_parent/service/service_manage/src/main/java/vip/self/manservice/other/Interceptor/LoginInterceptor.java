package vip.self.manservice.other.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import vip.self.concommon.exception.NFException;
import vip.self.uticommon.token.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token_");
        boolean b = JwtUtil.checkToken(token);
        Integer state = JwtUtil.getUserStateByJwtToken(token);
        if (!b || state == null) throw new NFException(20001, "未登录");
        if (state != 3) throw new NFException(20001, "该账户权限不够");
        return true;
    }
}
