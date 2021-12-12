package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class MonthValidator extends AbstractValidator {
    private static final String REGEXP = "^([1-9]|1[012])$";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
