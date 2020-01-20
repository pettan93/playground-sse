package cz.kalas.samples.dogstation.service;

import cz.kalas.samples.dogstation.AnotherCleverUtils;
import cz.kalas.samples.dogstation.model.PersonState;
import cz.kalas.samples.dogstation.model.entity.Person;
import cz.kalas.samples.dogstation.repository.DogRepository;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    private final DogRepository dogRepository;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void saveAll(Collection<Person> peoples) {
        personRepository.saveAll(peoples);
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

    public Person initDogReleasing(Person person) {

        log.debug("Initialized dog releasing for person" + person.toString());

        person.setPersonState(PersonState.IN_PROGRESS);

        return person;
    }


    @Transactional
    @Async
    public void releaseSomeDog(Integer id) {

        var person = personRepository.findById(id).get();
        AnotherCleverUtils.delay("Started async job for person " + person.toString());

        var futureResult = CompletableFuture.completedFuture(
                proccessDogRelesing(person)
        );


        futureResult.thenAccept(p -> {

            p.setPersonState(PersonState.IDLE);

            AnotherCleverUtils.delay("Now lets save person " + person.toString());

            personRepository.save(person);

            AnotherCleverUtils.delay("Saved! Task Done " + person.toString());
        });


    }


    private Person proccessDogRelesing(Person person) {

        AnotherCleverUtils.delay("Releasing some dog of person.." + person.toString());

        Random rand = new Random();

        if (personHasSomeDogs(person)) {
            var randomDog = person.getOwnedDogs()
                    .get(rand.nextInt(person.getOwnedDogs().size()));

            person.getOwnedDogs().remove(randomDog);
        } else {
            log.info("No dog released!");
        }

        AnotherCleverUtils.delay("Dog Released! " + person.toString());

        return person;
    }


    public Boolean personHasSomeDogs(Person person) {
        log.debug("Check if person have any dogs " + person.toString());
        return person.getOwnedDogs() != null && person.getOwnedDogs().size() > 0;
    }


}
