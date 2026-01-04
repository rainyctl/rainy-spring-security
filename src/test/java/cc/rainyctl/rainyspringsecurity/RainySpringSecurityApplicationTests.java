package cc.rainyctl.rainyspringsecurity;

import cc.rainyctl.rainyspringsecurity.entity.User;
import cc.rainyctl.rainyspringsecurity.mapper.UserMapper;
import cc.rainyctl.rainyspringsecurity.service.JwtService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
class RainySpringSecurityApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

	@Test
	void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
	}

    @Test
    void findUserByUsername() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername, "admin");
        User user =  userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    void checkDefaultPassword() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, "admin");
        User user =  userMapper.selectOne(wrapper);
        assertTrue(passwordEncoder.matches("test123",user.getPassword()));
    }

    @Test
    void signJwt() {
        String token = jwtService.generateToken("admin");
        System.out.println(token);
    }

    @Test
    void validateToken() {
        String token = jwtService.generateToken("admin");
        assertTrue(jwtService.validateToken(token));
        assertEquals("admin", jwtService.extractUsername(token));
    }
}
