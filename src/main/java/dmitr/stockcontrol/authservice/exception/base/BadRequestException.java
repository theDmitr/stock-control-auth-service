package dmitr.stockcontrol.authservice.exception.base;

public class BadRequestException extends CommonException {

    public BadRequestException(String i18nMessageKey, Object[] i18nArgs) {
        super(i18nMessageKey, i18nArgs);
    }

    public BadRequestException(String i18nMessageKey) {
        super(i18nMessageKey);
    }
}
