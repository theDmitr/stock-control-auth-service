package dmitr.stockcontrol.authservice.exception.handler.common;

import dmitr.stockcontrol.authservice.exception.base.BadRequestException;
import dmitr.stockcontrol.authservice.exception.base.CommonException;
import dmitr.stockcontrol.authservice.exception.base.NotFoundException;
import dmitr.stockcontrol.authservice.exception.base.UnauthorizedException;
import dmitr.stockcontrol.authservice.exception.handler.common.response.CommonErrorResponse;
import dmitr.stockcontrol.authservice.helper.I18nMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CommonExceptionHandler {

    private final I18nMessageProvider i18nMessageProvider;

    @ExceptionHandler
    public ResponseEntity<CommonErrorResponse> handle(NotFoundException e) {
        return generateErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CommonErrorResponse> handle(BadRequestException e) {
        return generateErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CommonErrorResponse> handle(UnauthorizedException e) {
        return generateErrorResponse(e, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<CommonErrorResponse> generateErrorResponse(CommonException e, HttpStatus status) {
        String localizedMessage = i18nMessageProvider.getMessage(e);
        CommonErrorResponse commonErrorResponse = CommonErrorResponse.builder()
                .message(localizedMessage)
                .code(status.value())
                .build();
        return new ResponseEntity<>(commonErrorResponse, status);
    }
}
