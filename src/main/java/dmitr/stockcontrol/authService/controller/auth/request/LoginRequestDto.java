package dmitr.stockcontrol.authService.controller.auth.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {

    private String username;
    private String password;
}
