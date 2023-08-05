package com.people.peoplemanager.api;

import com.people.peoplemanager.dto.PersonDto;
import com.people.peoplemanager.exceptions.InvalidObjectException;
import com.people.peoplemanager.exceptions.ObjectNotFoundException;
import com.people.peoplemanager.model.Person;
import com.people.peoplemanager.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class PeopleController {

    private final PersonRepository repository;

    public PeopleController(PersonRepository repository) {
        this.repository = repository;
    }

    private void verificacaoString(String text, int tam, String namefield){
        if(text.isEmpty() || text.split("").equals("") && namefield.length() != 6){
            throw new InvalidObjectException("Os campos \"apelido\", \"nome\" e \"nascimento\" não podem ser vazios ou nulos");
        }else if(text.length() > tam){
            throw new InvalidObjectException("O campo " + namefield + " deve ter no maximo " + tam + " caracteres.");
        } else if(repository.existsByApelido(text) && namefield.equals("Apelido")) {
            throw new InvalidObjectException(namefield + " ja existente na base de dados, por favor tente outro.");
        }else if(text.matches("/\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d/gm") && namefield.equals("Nascimento")){

        }
    }
    @PostMapping
    public ResponseEntity add(@RequestBody PersonDto person) {
        System.out.println(person.apelido());
        System.out.println(person.nascimento());
        System.out.println(person.nome());
        System.out.println(person.stacks());

        verificacaoString(person.nome(), 100, "Nome");
        verificacaoString(person.apelido(), 32, "Apelido");
        verificacaoString(person.nascimento(), 10, "Nascimento");
        for(String s : person.stacks()){
            verificacaoString(s, 32, "stacks");
        }

        Person pessoa = new Person(null, person.apelido(), person.nome(), person.nascimento(), person.stacks());
        pessoa = repository.save(pessoa);

        return (ResponseEntity) ResponseEntity.status(HttpStatus.CREATED).header(String.valueOf(pessoa.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity buscaPorId(@PathVariable String stringId){
        UUID id = UUID.fromString(stringId);
        if(repository.existsById(id)){
            return ResponseEntity.ok(repository.findById(id));
        }
        throw new ObjectNotFoundException("Pessoa não encontrada através do ID informado");
    }

    @GetMapping
    public ResponseEntity getTest(){
        return ResponseEntity.ok("Teste deu certo");
    }



}
