package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class EmailValidator extends AbstractValidator {
    private final String REGEXP = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
