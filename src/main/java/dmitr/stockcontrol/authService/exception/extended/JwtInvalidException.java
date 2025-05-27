package dmitr.stockcontrol.authService.exception.extended;

import dmitr.stockcontrol.authService.exception.base.UnauthorizedException;

public class JwtInvalidException extends UnauthorizedException {

    public JwtInvalidException() {
        super("auth.jwt.invalid");
    }
}
