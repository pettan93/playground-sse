package cz.kalas.samples.dogstation.endpoint;

import cz.kalas.samples.dogstation.AnotherCleverUtils;
import cz.kalas.samples.dogstation.model.dto.PersonDto;
import cz.kalas.samples.dogstation.model.entity.Person;
import cz.kalas.samples.dogstation.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.DestinationSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonEndpoint {

    private final PersonService personService;


    @GetMapping(value = "/person/list")
    public ResponseEntity<List<PersonDto>> getAll() {
        log.debug("Get all");

        return ResponseEntity.ok(
                personService.getAll().stream()
                        .map(this::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/person/getRandom")
    public ResponseEntity<Person> getRandom() {
        log.debug("Get random");

        var randomPerson = personService.getRandom();

        return randomPerson.isPresent() ?
                ResponseEntity.of(randomPerson) : ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/person/{id}/releaseDog")
    public ResponseEntity<PersonDto> releaseSomeDog(@PathVariable Integer id) {
        log.debug("releaseSomeDog");

        var person = personService.getPersonById(id);

        if (person.isPresent()) {

            AnotherCleverUtils.delay(AnotherCleverUtils.DELAY_CONSTANT);

            log.debug("Requst - release some dog of exting user " + person.get());

            var result = personService.releaseSomeDog(person.get());

            return ResponseEntity.ok(toDtoFull(result));

        }

        return ResponseEntity.badRequest().build();
    }


    public PersonDto toDto(Person person) {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Person.class, PersonDto.class)
                .addMappings(mapper -> mapper.skip(PersonDto::setOwnedDogs));

        return modelMapper.map(person, PersonDto.class);
    }

    public PersonDto toDtoFull(Person person) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(person, PersonDto.class);
    }


}

