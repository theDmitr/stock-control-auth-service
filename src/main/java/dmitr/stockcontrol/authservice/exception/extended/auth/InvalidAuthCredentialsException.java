package dmitr.stockcontrol.authservice.exception.extended.auth;

import dmitr.stockcontrol.authservice.exception.base.UnauthorizedException;

public class InvalidAuthCredentialsException extends UnauthorizedException {

    public InvalidAuthCredentialsException() {
        super("auth.credentials.invalid");
    }
}
