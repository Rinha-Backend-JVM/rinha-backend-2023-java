package com.people.peoplemanager.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "PERSON_TB")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 32)
    private String  apelido;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 10)
    private String nascimento;
    private String[] stack; //FIXME: CAMPO NÃO ESTÁ SENDO PERSISTIDO

    public Person() {
    }

    public Person(UUID id, String apelido, String nome, String nascimento, String[] stack) {
        this.id = id;
        this.apelido = apelido;
        this.nome = nome;
        this.nascimento = nascimento;
        this.stack = stack;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String[] getStack() {
        return stack;
    }

    public void setStack(String[] stack) {
        this.stack = stack;
    }
}

