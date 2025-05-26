package dmitr.stockcontrol.authservice.exception.base;

public class NotFoundException extends CommonException {

    public NotFoundException(String i18nMessageKey) {
        super(i18nMessageKey);
    }
}
