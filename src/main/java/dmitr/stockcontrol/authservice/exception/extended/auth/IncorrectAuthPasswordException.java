package dmitr.stockcontrol.authservice.exception.extended.auth;

import dmitr.stockcontrol.authservice.exception.base.UnauthorizedException;

public class IncorrectAuthPasswordException extends UnauthorizedException {

    public IncorrectAuthPasswordException() {
        super("auth.password.incorrect");
    }
}
