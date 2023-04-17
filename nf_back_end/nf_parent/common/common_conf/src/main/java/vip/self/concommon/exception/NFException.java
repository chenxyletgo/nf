package vip.self.concommon.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NFException extends RuntimeException {

    private Integer code;
    private String msg;

}
