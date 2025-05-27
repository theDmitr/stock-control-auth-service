package dmitr.stockcontrol.authService.controller.auth.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshRequestDto {

    private String refreshToken;
}
