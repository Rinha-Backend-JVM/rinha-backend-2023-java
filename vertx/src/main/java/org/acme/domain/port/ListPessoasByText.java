package org.acme.domain.port;

import io.smallrye.mutiny.Multi;
import org.acme.domain.Pessoa;

public interface ListPessoasByText {

    Multi<Pessoa> byText(String searchText);

}
