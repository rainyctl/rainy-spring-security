package cc.rainyctl.rainyspringsecurity.mapper;

import cc.rainyctl.rainyspringsecurity.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("""
        SELECT DISTINCT m.*
        FROM sys_menu m
        JOIN sys_role_menu rm ON m.id = rm.menu_id
        JOIN sys_user_role ur ON rm.role_id = ur.role_id
        JOIN sys_role r ON r.id = ur.role_id
        WHERE ur.user_id = #{userId}
            AND m.status = 1
            AND r.status = 1
    """)
    List<Menu> selectMenusByUserId(Long userId);

    @Select("""
        SELECT DISTINCT m.perm_key
        FROM sys_menu m
        JOIN sys_role_menu rm ON m.id = rm.menu_id
        JOIN sys_user_role ur ON rm.role_id = ur.role_id
        JOIN sys_role r ON r.id = ur.role_id
        WHERE ur.user_id = #{userId}
            AND m.status = 1
            AND r.status = 1
    """)
    List<String> selectPermKeysByUserId(Long userId);
}
