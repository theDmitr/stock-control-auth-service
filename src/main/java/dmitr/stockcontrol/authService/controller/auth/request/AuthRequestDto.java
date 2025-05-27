package dmitr.stockcontrol.authService.controller.auth.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthRequestDto {

    private String username;
    private String password;
}
