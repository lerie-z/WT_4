package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class HourValidator extends AbstractValidator {
    private static final String REGEXP = "^((1[0-9])|(0)|(2[0-3]))$";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
