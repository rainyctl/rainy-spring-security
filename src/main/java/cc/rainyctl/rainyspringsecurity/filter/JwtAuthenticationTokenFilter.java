package cc.rainyctl.rainyspringsecurity.filter;

import cc.rainyctl.rainyspringsecurity.entity.LoginUser;
import cc.rainyctl.rainyspringsecurity.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@NullMarked
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. get token
        String token = request.getHeader("token");

        // 2. if there is no token, continue
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. parse JWT and extract subject (userId)
        Long userId;
        try {
            userId = jwtService.extractUserId(token);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid or expired token");
        }

        // 4. retrieve user info from Redis
        String redisKey = "login:" + userId;
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(redisKey);

        // 5. if Redis does not contain login info
        // then user is not logged in anymore, need to do login again
        if (loginUser == null) {
            throw new InsufficientAuthenticationException("Login session expired");
        }

        // 6. create Authentication object
        // when use the constructor of 3 args, it also set it as authenticated
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser,
                null,
                loginUser.getAuthorities());

        // 7. store authentication into SecurityContext for other filters
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 8. continue filter chain
        filterChain.doFilter(request, response);
    }
}
