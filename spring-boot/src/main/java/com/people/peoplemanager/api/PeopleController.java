package com.people.peoplemanager.api;

import com.people.peoplemanager.dto.PersonDto;
import com.people.peoplemanager.exceptions.InvalidObjectException;
import com.people.peoplemanager.exceptions.PersonNotFoundException;
import com.people.peoplemanager.model.Person;
import com.people.peoplemanager.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class PeopleController {

    private final PeopleRepository repository;

    public PeopleController(PeopleRepository repository) {
        this.repository = repository;
    }

    private void verificacaoString(String text, int tam, String namefield){
        if(text == null || text.isEmpty() || text.split("").equals("") && namefield.length() != 6){
            throw new InvalidObjectException();
        }else if(text.length() > tam){
            throw new InvalidObjectException();
        } else if(repository.existsByApelido(text) && namefield.equals("Apelido")) {
            throw new InvalidObjectException();
        }else if(namefield.equals("Nascimento") && text.contains("/\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d/gm")){    //FIXME: VALIDAÇÃO DA DATA NÃO ESTA FUNCIONANDO
            throw new InvalidObjectException();
        }
    }
    @PostMapping
    @Transactional
    public ResponseEntity add(@RequestBody PersonDto person) {
        verificacaoString(person.nome(), 100, "Nome");
        verificacaoString(person.apelido(), 32, "Apelido");
        verificacaoString(person.nascimento(), 10, "Nascimento");
        if(person.stack() != null){
            for(String s : person.stack()){
                verificacaoString(s, 32, "stacks");
            }
        }

        Person pessoa = new Person(null, person.apelido(), person.nome(), person.nascimento(), person.stack());
        pessoa = repository.save(pessoa);

        return ResponseEntity.status(HttpStatus.CREATED).header("Id", String.valueOf(pessoa.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity buscaPorId(@PathVariable(value = "id") String stringId){
        UUID id = UUID.fromString(stringId);
        if(repository.existsById(id)){
            return ResponseEntity.ok(repository.findById(id));
        }
        throw new PersonNotFoundException();
    }

}
