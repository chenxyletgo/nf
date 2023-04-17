package vip.self.uticommon.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {

    public static final long EXPIRE = 1000 * 60 * 60 * 24; // token过期时间
    public static final String APP_SECRET = "6KBrgZnYgSXTOV7gN6K3fgvlhsFCnb"; // 秘钥

    /**
     * 生成token字符串
     * @param id 用户id
     * @param name 用户姓名
     * @return token字符串
     */
    public static String getJwtToken(String id, String name, String state){
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("nf-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)  //设置token主体部分 ，存储用户信息
                .claim("name", name)
                .claim("state", state)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return JwtToken;
    }

    /**
     * 判断token字符串是否存在与有效
     * @param token token字符串
     * @return true有效，false无效
     */
    public static boolean checkToken(String token) {
        if("".equals(token) || token == null) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断请求包中的token是否存在与有效
     * @param request 请求包
     * @return true有效，false无效
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if("".equals(jwtToken) || jwtToken == null) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 从token中翻译出用户id
     * @param token token字符串
     * @return 用户id
     */
    public static String getUserIdByJwtToken(String token) {
        if("".equals(token) || token == null) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }

    /**
     * 从请求包中的token中翻译出用户id
     * @param request 请求包
     * @return 用户id
     */
    public static String getUserIdByJwtToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if("".equals(token) || token == null) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }

    /**
     * 从token字符串中翻译出用户state
     * @param token token字符串
     * @return state
     */
    public static Integer getUserStateByJwtToken(String token) {
        if("".equals(token) || token == null) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return Integer.parseInt((String) claims.get("state"));
    }

    /**
     * 从请求包翻译出用户state
     * @param request 请求包
     * @return state
     */
    public static Integer getUserStateByJwtToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if("".equals(token) || token == null) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return Integer.parseInt((String) claims.get("state"));
    }

}

