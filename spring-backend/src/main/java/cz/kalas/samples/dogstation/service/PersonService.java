package cz.kalas.samples.dogstation.service;

import cz.kalas.samples.dogstation.AnotherCleverUtils;
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

    private final static long DELAY_CONSTANT = 2000; // milisecs

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
        log.debug("Lets release some dog for person " + person.toString());

        AnotherCleverUtils.delay(DELAY_CONSTANT);

        Random rand = new Random();

        if (personHasSomeDogs(person)) {
            var randomDog = person.getOwnedDogs()
                    .get(rand.nextInt(person.getOwnedDogs().size()));

            person.getOwnedDogs().remove(randomDog);
        } else {
            log.info("No dog released!");
        }

        AnotherCleverUtils.delay(DELAY_CONSTANT);

        log.debug("OK, now lets save person " + person.toString());
        return personRepository.save(person);
    }


    public Boolean personHasSomeDogs(Person person) {
        log.debug("Check if person have any dogs " + person.toString());
        return person.getOwnedDogs() != null && person.getOwnedDogs().size() > 0;
    }


}
