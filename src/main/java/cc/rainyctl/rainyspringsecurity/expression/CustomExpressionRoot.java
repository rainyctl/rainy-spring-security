package cc.rainyctl.rainyspringsecurity.expression;

import cc.rainyctl.rainyspringsecurity.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cer")
public class CustomExpressionRoot {

    public boolean hasModuleAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if (loginUser == null) {
            return false;
        }
        List<String> permissions = loginUser.getPermissions();
        for  (String permission : permissions) {
            if (permission.contains(":")) {
                String module = permission.split(":")[0];
                if (authority.startsWith(module + ":")) {
                    return true;
                }
            }
        }
        return false;
    }
}
