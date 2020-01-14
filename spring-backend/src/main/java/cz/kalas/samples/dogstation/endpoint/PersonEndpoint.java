package cz.kalas.samples.dogstation.endpoint;

import cz.kalas.samples.dogstation.model.entity.Person;
import cz.kalas.samples.dogstation.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonEndpoint {

    private final PersonService personService;


    @GetMapping(value = "/person/list")
    public ResponseEntity<List<Person>> getAll() {
        log.debug("Get all");

        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping(value = "/person/getRandom")
    public ResponseEntity<Person> getRandom() {
        log.debug("Get random");

        var randomPerson = personService.getRandom();

        return randomPerson.isPresent() ?
                ResponseEntity.of(randomPerson) : ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/person/{id}/releaseDog")
    public ResponseEntity<Person> releaseSomeDog(@PathVariable Integer id) {
        log.debug("releaseSomeDog");

        var person = personService.getPersonById(id);

        if (person.isPresent()) {

            var result = personService.releaseSomeDog(person.get());

            return ResponseEntity.ok(result);

        }

        return ResponseEntity.badRequest().build();
    }


}

