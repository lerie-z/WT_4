package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class DateValidator extends AbstractValidator {
    private static final String REGEXP = "^(([1-9])|(1[0-9])|(2[0-9])|(3[0-1]))$";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
