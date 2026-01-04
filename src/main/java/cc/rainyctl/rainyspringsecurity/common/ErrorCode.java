package cc.rainyctl.rainyspringsecurity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(20000, "success"),

    // Client
    PARAM_INVALID(40001, "invalid parameter"),

    // Auth
    LOGIN_FAILED(40101, "username or password is incorrect"),
    TOKEN_EXPIRED(40102, "token expired"),

    // Permission
    NO_PERMISSION(40301, "no permission"),

    // Server
    SERVER_ERROR(50000, "server error");

    private final int code;
    private final String message;
}
