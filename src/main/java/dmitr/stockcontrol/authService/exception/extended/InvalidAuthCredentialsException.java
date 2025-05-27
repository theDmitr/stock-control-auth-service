package dmitr.stockcontrol.authService.exception.extended;

import dmitr.stockcontrol.authService.exception.base.UnauthorizedException;

public class InvalidAuthCredentialsException extends UnauthorizedException {

    public InvalidAuthCredentialsException() {
        super("auth.credentials.invalid");
    }
}
