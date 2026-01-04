package cc.rainyctl.rainyspringsecurity.controller;

import cc.rainyctl.rainyspringsecurity.common.R;
import cc.rainyctl.rainyspringsecurity.service.LoginService;
import cc.rainyctl.rainyspringsecurity.vo.request.LoginRequest;
import cc.rainyctl.rainyspringsecurity.vo.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public R<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = loginService.login(request);
        return R.ok(loginResponse);
    }

    @PostMapping("/logout")
    public R<Object> logout() {
        try {
            loginService.logout();
            return R.ok().message("logout success");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail().message("logout failed");
        }
    }
}
