package org.acme.domain;

import java.util.List;

public class PessoaInvalidError extends RuntimeException {

    private final List<String> validationErrors;

    public PessoaInvalidError(List<String> validationErrors) {
        super("Validation exception");
        this.validationErrors = validationErrors;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
