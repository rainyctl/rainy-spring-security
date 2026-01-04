package cc.rainyctl.rainyspringsecurity;

import cc.rainyctl.rainyspringsecurity.entity.User;
import cc.rainyctl.rainyspringsecurity.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RainySpringSecurityApplicationTests {

    @Autowired
    private UserMapper userMapper;

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
}
