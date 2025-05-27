package dmitr.stockcontrol.authService.exception.base;

public class NotFoundException extends CommonException {

    public NotFoundException(String i18nMessageKey) {
        super(i18nMessageKey);
    }
}
