package dmitr.stockcontrol.authService.controller.auth.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponseDto {

    private String accessToken;
    private String refreshToken;
}
