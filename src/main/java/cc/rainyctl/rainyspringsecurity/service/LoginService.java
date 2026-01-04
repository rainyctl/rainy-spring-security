package cc.rainyctl.rainyspringsecurity.service;

import cc.rainyctl.rainyspringsecurity.vo.request.LoginRequest;
import cc.rainyctl.rainyspringsecurity.vo.response.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest loginRequest);
}
