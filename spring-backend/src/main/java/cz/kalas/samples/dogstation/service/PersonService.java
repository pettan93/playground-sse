package cz.kalas.samples.dogstation.service;

import cz.kalas.samples.dogstation.model.entity.Person;
import cz.kalas.samples.dogstation.repository.DogRepository;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Person releaseSomeDog(Person person) {

        Random rand = new Random();

        if(personHasSomeDogs(person)){
            var randomDog = person.getOwnedDogs()
                    .get(rand.nextInt(person.getOwnedDogs().size()));

            person.getOwnedDogs().remove(randomDog);
        }else {
            log.info("No dog released!");
        }

        return personRepository.save(person);
    }


    public Boolean personHasSomeDogs(Person person) {
        return person.getOwnedDogs() != null && person.getOwnedDogs().size() > 0;
    }


}
