package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class MinuteValidator extends AbstractValidator {
    private static final String REGEXP = "^(([1-5]?([0-9])))$";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
