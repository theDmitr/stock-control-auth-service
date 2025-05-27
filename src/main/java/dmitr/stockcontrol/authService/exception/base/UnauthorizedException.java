package dmitr.stockcontrol.authService.exception.base;

public class UnauthorizedException extends CommonException {

    public UnauthorizedException(String i18nMessageKey) {
        super(i18nMessageKey);
    }
}
