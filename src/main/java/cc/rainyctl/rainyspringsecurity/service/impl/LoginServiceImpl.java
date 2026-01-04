package cc.rainyctl.rainyspringsecurity.service.impl;

import cc.rainyctl.rainyspringsecurity.entity.LoginUser;
import cc.rainyctl.rainyspringsecurity.service.JwtService;
import cc.rainyctl.rainyspringsecurity.service.LoginService;
import cc.rainyctl.rainyspringsecurity.vo.request.LoginRequest;
import cc.rainyctl.rainyspringsecurity.vo.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. create an Authentication object (UsernamePasswordAuthenticationToken implements it)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        // 2. let AuthenticationManager checks it. throws AuthenticationException if it fails
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        LoginUser principal = (LoginUser) authentication.getPrincipal();

        if (principal == null) {
            throw new RuntimeException("Login failed");
        }

        // 3. generate JWT token
        Long userId = principal.getUser().getId();
        String jwt = jwtService.generateToken(userId, principal.getUsername());

        // 4. save LoginUser in Redis (Key = login:userId) to reduce DB access later
        String redisKey = "login:" + userId;
        redisTemplate.opsForValue().set(redisKey, principal);

        return new LoginResponse(jwt);
    }
}
