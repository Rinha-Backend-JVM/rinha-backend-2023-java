package org.acme.domain.port;

import io.smallrye.mutiny.Uni;
import org.acme.domain.Pessoa;
import org.acme.domain.PessoaInvalidError;

public interface CreatePessoa {

    Uni<Pessoa> create(Pessoa pessoa) throws PessoaInvalidError;

}
