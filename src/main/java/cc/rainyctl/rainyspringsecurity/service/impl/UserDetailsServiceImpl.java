package cc.rainyctl.rainyspringsecurity.service.impl;

import cc.rainyctl.rainyspringsecurity.entity.LoginUser;
import cc.rainyctl.rainyspringsecurity.entity.User;
import cc.rainyctl.rainyspringsecurity.mapper.MenuMapper;
import cc.rainyctl.rainyspringsecurity.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NullMarked
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    private final MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. load user
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        // 2. query permissions
        List<String> permKeys = menuMapper.selectPermKeysByUserId(user.getId());

        return new LoginUser(user, permKeys);
    }
}
