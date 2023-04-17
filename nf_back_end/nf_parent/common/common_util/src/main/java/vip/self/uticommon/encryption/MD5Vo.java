package vip.self.uticommon.encryption;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MD5Vo {

    private String salt;
    private String password;

}
