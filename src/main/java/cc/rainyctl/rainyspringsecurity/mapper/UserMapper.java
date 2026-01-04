package cc.rainyctl.rainyspringsecurity.mapper;

import cc.rainyctl.rainyspringsecurity.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
