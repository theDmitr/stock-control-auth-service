package dmitr.stockcontrol.authservice.exception.extended.auth;

import dmitr.stockcontrol.authservice.exception.base.UnauthorizedException;

public class NotFoundAuthUsernameException extends UnauthorizedException {

    public NotFoundAuthUsernameException() {
        super("auth.username.notFound");
    }
}
