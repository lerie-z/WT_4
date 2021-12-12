package server.service.validator;

import server.service.validator.impl.*;

public class ValidatorFactory {
    public AbstractValidator getEmailValidator() {
        return new EmailValidator();
    }

    public AbstractValidator getDateValidator() {
        return new DateValidator();
    }

    public AbstractValidator getIdValidator() {
        return new IdValidator();
    }

    public AbstractValidator getMinuteValidator() {
        return new MinuteValidator();
    }

    public AbstractValidator getHourValidator() {
        return new HourValidator();
    }

    public AbstractValidator getYearValidator() {
        return new YearValidator();
    }

    public AbstractValidator getMonthValidator() {
        return new MonthValidator();
    }

    public AbstractValidator getNameValidator() {
        return new NameValidator();
    }

    public AbstractValidator getPriceValidator() {
        return new PriceValidator();
    }

    public AbstractValidator getPhoneValidator() {
        return new PhoneValidator();
    }
}
