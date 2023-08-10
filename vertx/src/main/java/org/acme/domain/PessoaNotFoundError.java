package org.acme.domain;

import java.util.UUID;

public class PessoaNotFoundError extends RuntimeException {

    public PessoaNotFoundError(UUID uuid) {
        super("Pessoa com id %s não encontrada".formatted(uuid.toString()));
    }
}
