package org.acme.adapter;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import org.acme.domain.Pessoa;
import org.acme.domain.PessoaInvalidError;
import org.acme.domain.PessoaNotFoundError;
import org.acme.domain.port.CreatePessoa;
import org.acme.domain.port.GetPessoaById;
import org.acme.domain.port.ListPessoasByText;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class PgQueriesAdapter implements ListPessoasByText, GetPessoaById, CreatePessoa {

    @Inject
    io.vertx.mutiny.pgclient.PgPool pgClient;

    @Override
    public Uni<Pessoa> create(Pessoa pessoa) throws PessoaInvalidError {
        return pgClient.preparedQuery("insert into public.pessoa(apelido, nome, nascimento, stack) VALUES ($1, $2, $3, $4) returning id")
                .execute(Tuple.of(pessoa.apelido(), pessoa.nome(), pessoa.dataDeNascimento(), String.join(",", pessoa.stack())))
                .onItem().transform(rs -> {
                    UUID id = rs.iterator().next().getUUID("id");
                    return new Pessoa(id.toString(), pessoa.apelido(), pessoa.nome(), pessoa.dataDeNascimento(), pessoa.stack());
                })
                .onFailure().invoke(Unchecked.consumer((ex) -> {
                    if (ex instanceof PgException) {
                        throw new PessoaInvalidError(Collections.singletonList(((PgException) ex).getErrorMessage()));
                    }
                }));
    }

    @Override
    public Uni<Pessoa> byId(UUID id) {
         return pgClient.preparedQuery("select * from public.pessoa where id = $1").execute(Tuple.of(id))
                 .onItem().transform(RowSet::iterator)
                 .onItem().transform(iterator -> iterator.hasNext() ? fromRow(iterator.next()) : null)
                 .onItem().ifNull().failWith(new PessoaNotFoundError(id));
    }

    @Override
    public Multi<Pessoa> byText(String searchText) {
        return pgClient.preparedQuery("select * from public.pessoa where " +
                        "to_tsvector(apelido || ' ' || nome || ' ' || stack) @@ plainto_tsquery($1)")
                .execute(Tuple.of(searchText))
                .onItem().transformToMulti(rs -> Multi.createFrom().iterable(rs))
                .onItem().transform(this::fromRow);
    }

    private Pessoa fromRow(Row row) {
        return new Pessoa(
                row.getUUID("id").toString(),
                row.getString("apelido"),
                row.getString("nome"),
                row.getLocalDate("nascimento"),
                Optional.ofNullable(row.getString("stack")).map(text -> List.of(text.split(","))).orElse(null)
        );
    }

}
