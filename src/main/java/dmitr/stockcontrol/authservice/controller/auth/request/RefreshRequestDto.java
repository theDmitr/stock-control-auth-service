package dmitr.stockcontrol.authservice.controller.auth.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshRequestDto {

    private String refreshToken;
}
