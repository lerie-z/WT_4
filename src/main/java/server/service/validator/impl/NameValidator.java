package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class NameValidator extends AbstractValidator {
    private static final String REGEXP = "^[A-zА-я]{2-30}$";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
