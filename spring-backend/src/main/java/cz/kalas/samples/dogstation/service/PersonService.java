package cz.kalas.samples.dogstation.service;

import cz.kalas.samples.dogstation.events.StateChangeEvent;
import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.model.DogBreed;
import cz.kalas.samples.dogstation.model.DogStationState;
import cz.kalas.samples.dogstation.model.Person;
import cz.kalas.samples.dogstation.repository.DogRepository;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    public Person releaseSomeDog(Person person) {

        Random rand = new Random();

        var randomDog = person.getOwnedDogs()
                .get(rand.nextInt(person.getOwnedDogs().size()));

        person.getOwnedDogs().remove(randomDog);

        return personRepository.save(person);
    }


}
