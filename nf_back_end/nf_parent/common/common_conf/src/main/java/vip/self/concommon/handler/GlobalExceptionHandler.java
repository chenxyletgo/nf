package vip.self.concommon.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.self.concommon.exception.NFException;
import vip.self.uticommon.result.R;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 只要出现异常就会执行
     * @param e 异常对象
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().setMessage("服务器异常");
    }

    /**
     * 自定义异常
     * @param e 异常对象
     * @return 响应
     */
    @ExceptionHandler(NFException.class)
    @ResponseBody
    public R error(NFException e) {
        e.printStackTrace();
        return R.error().setCode(e.getCode()).setMessage(e.getMsg());
    }

}
