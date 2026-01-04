package cc.rainyctl.rainyspringsecurity.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 32, message = "Password length must be between 6 and 32")
    private String password;
}
