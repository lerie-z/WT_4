package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class YearValidator extends AbstractValidator {
    private static final String REGEXP = "^[0-9]{4}$";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
