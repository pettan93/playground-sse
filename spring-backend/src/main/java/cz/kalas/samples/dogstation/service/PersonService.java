package cz.kalas.samples.dogstation.service;

import cz.kalas.samples.dogstation.AnotherCleverUtils;
import cz.kalas.samples.dogstation.model.PersonState;
import cz.kalas.samples.dogstation.model.entity.Person;
import cz.kalas.samples.dogstation.repository.DogRepository;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    private final DogRepository dogRepository;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Optional<Person> getPersonById(Integer id) {
        return personRepository.findById(id);
    }

    public List<Person> getAll() {

        return personRepository.getAll();
    }

    public Optional<Person> getRandom() {
        Random rand = new Random();
        List<Integer> ids = personRepository.getIds();
        var randomId = ids.get(rand.nextInt(ids.size()));
        return getPersonById(randomId);
    }


    @Transactional
    public Person releaseSomeDog(Person person) {

        AnotherCleverUtils.delay("Releasing some dog of person.." + person.toString());

        Random rand = new Random();

        if (personHasSomeDogs(person)) {
            var randomDog = person.getOwnedDogs()
                    .get(rand.nextInt(person.getOwnedDogs().size()));

            person.getOwnedDogs().remove(randomDog);
        } else {
            log.info("No dog released!");
        }


        AnotherCleverUtils.delay("Released! now lets save person " + person.toString());

        var saved = personRepository.save(person);

        AnotherCleverUtils.delay("Saved! Lets return saved person " + person.toString());

        return saved;
    }


    public Boolean personHasSomeDogs(Person person) {
        log.debug("Check if person have any dogs " + person.toString());
        return person.getOwnedDogs() != null && person.getOwnedDogs().size() > 0;
    }


}
