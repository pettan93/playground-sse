package cz.kalas.samples.dogstation.service;

import cz.kalas.samples.dogstation.model.Person;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Optional<Person> getPersonById(Integer id) {
        return personRepository.findById(id);
    }

    public Optional<Person> getRandom() {
        Random rand = new Random();
        var randomId = rand.nextInt((int) personRepository.count());
        return getPersonById(randomId);
    }

    public Person releaseSomeDog(Person person) {

        Random rand = new Random();

        var randomDog = person.getOwnedDogs()
                .get(rand.nextInt(person.getOwnedDogs().size()));

        person.getOwnedDogs().remove(randomDog);

        return personRepository.save(person);
    }


}
