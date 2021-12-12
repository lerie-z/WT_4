package server.service.validator.impl;

import server.service.validator.AbstractValidator;

public class PriceValidator extends AbstractValidator {
    private static final String REGEXP = "^(([1-9]\\d*\\.\\d+)|(0[1-9]*\\.\\d+)|(\\d\\.\\d+)|(0)|([1-9]\\d*))$";

    @Override
    protected String getRegex() {
        return REGEXP;
    }
}
