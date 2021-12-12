package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class IdValidator extends AbstractValidator {
    private static final String REGEXP = "(?<![-.])\\b[0-9]+\\b(?!\\.[0-9])";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
