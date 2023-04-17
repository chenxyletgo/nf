package vip.self.uticommon.result;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class R {

    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    private R() {}

    public static R ok() {
        R r = new R();
        r.setSuccess(true).setCode(ResultCode.SUCCESS).setMessage("成功");
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(false).setCode(ResultCode.ERROR).setMessage("失败");
        return r;
    }

    public static R warn() {
        R r = new R();
        r.setSuccess(false).setCode(ResultCode.WARN).setMessage("失败但不提示");
        return r;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

}
