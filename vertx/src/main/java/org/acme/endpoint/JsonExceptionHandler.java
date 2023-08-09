package org.acme.endpoint;

import io.smallrye.mutiny.CompositeException;
import org.acme.domain.PessoaInvalidError;
import org.acme.domain.PessoaNotFoundError;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Collections;
import java.util.List;

@Provider
public class JsonExceptionHandler implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        if (e instanceof CompositeException) {
            e = (RuntimeException) e.getSuppressed()[0];
        }

        if (e instanceof PessoaNotFoundError) {
            return Response.status(404).entity(new ErrorMessages(Collections.singletonList(e.getMessage()))).build();
        } else if (e instanceof PessoaInvalidError) {
            return Response.status(422).entity(new ErrorMessages(((PessoaInvalidError) e).getValidationErrors())).build();
        }

        return Response.status(500).entity(e.getMessage()).build();
    }

    private record ErrorMessages(List<String> messages) {}

}
