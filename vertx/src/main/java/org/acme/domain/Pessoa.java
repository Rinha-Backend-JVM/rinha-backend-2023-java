package org.acme.domain;

import java.time.LocalDate;
import java.util.List;

public record Pessoa(String id, String apelido, String nome, LocalDate dataDeNascimento, List<String> stack) {

}
