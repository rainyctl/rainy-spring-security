package cc.rainyctl.rainyspringsecurity.common;

import lombok.Data;

@Data
public class R<T> {

    private Integer code;
    private String message;
    private T data;

    private R() {}

    public static <T> R<T> ok() {
        return new R<T>()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage());
    }

    public static <T> R<T> ok(T data) {
        return new R<T>()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .data(data);
    }

    public static <T> R<T> fail() {
        return new R<T>()
                .code(ErrorCode.SERVER_ERROR.getCode())
                .message(ErrorCode.SERVER_ERROR.getMessage());
    }

    public static <T> R<T> fail(String message) {
        return new R<T>()
                .code(ErrorCode.SERVER_ERROR.getCode())
                .message(message);
    }

    public static <T> R<T> fail(Integer code, String message) {
        return new R<T>()
                .code(code)
                .message(message);
    }

    public R<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public R<T> message(String message) {
        this.message = message;
        return this;
    }

    public R<T> data(T data) {
        this.data = data;
        return this;
    }
}
