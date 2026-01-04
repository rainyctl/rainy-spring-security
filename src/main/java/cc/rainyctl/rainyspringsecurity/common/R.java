package cc.rainyctl.rainyspringsecurity.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class R<T> {

    private Integer code;
    private String message;
    private T data;

    private R() {}

    public static <T> R<T> ok() {
        return new R<T>()
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMessage(ErrorCode.SUCCESS.getMessage());
    }

    public static <T> R<T> ok(T data) {
        return new R<T>()
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMessage(ErrorCode.SUCCESS.getMessage())
                .setData(data);
    }

    public static <T> R<T> fail() {
        return new R<T>()
                .setCode(ErrorCode.SERVER_ERROR.getCode())
                .setMessage(ErrorCode.SERVER_ERROR.getMessage());
    }

    public static <T> R<T> fail(String message) {
        return new R<T>()
                .setCode(ErrorCode.SERVER_ERROR.getCode())
                .setMessage(message);
    }

    public static <T> R<T> fail(Integer code, String message) {
        return new R<T>()
                .setCode(code)
                .setMessage(message);
    }
}
