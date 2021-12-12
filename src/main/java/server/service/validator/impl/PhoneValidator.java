package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class PhoneValidator extends AbstractValidator {
    private static final String REGEXP = "\\+?375\\d{6,11}$";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
