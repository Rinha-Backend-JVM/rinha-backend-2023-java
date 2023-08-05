package com.people.peoplemanager.repository;

import com.people.peoplemanager.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    boolean existsByApelido(String apelido);

}
