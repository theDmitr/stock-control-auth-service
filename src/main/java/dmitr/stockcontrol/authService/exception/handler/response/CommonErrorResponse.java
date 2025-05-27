package dmitr.stockcontrol.authService.exception.handler.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonErrorResponse {

    private String message;
    private Integer code;
}
