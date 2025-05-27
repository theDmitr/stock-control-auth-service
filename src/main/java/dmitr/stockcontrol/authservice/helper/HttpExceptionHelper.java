package dmitr.stockcontrol.authservice.helper;

import dmitr.stockcontrol.authservice.exception.base.CommonException;
import dmitr.stockcontrol.authservice.exception.handler.common.response.CommonErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpExceptionHelper {

    private final I18nMessageProvider i18nMessageProvider;

    public ResponseEntity<CommonErrorResponse> generateErrorResponseEntity(CommonException e, HttpStatus status) {
        CommonErrorResponse errorResponse = generateErrorResponse(e, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    public CommonErrorResponse generateErrorResponse(CommonException e, HttpStatus status) {
        String localizedMessage = i18nMessageProvider.getMessage(e);
        return CommonErrorResponse.builder()
                .message(localizedMessage)
                .code(status.value())
                .build();
    }
}
