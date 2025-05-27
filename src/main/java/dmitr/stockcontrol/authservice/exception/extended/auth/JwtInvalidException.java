package dmitr.stockcontrol.authservice.exception.extended.auth;

import dmitr.stockcontrol.authservice.exception.base.UnauthorizedException;

public class JwtInvalidException extends UnauthorizedException {

    public JwtInvalidException() {
        super("auth.jwt.invalid");
    }
}
