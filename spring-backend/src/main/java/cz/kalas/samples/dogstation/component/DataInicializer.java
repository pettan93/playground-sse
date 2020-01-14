package cz.kalas.samples.dogstation.component;

import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.model.DogBreed;
import cz.kalas.samples.dogstation.model.Person;
import cz.kalas.samples.dogstation.model.Toy;
import cz.kalas.samples.dogstation.repository.DogRepository;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import cz.kalas.samples.dogstation.repository.ToysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInicializer {

    private final DogRepository dogRepository;

    private final PersonRepository personRepository;

    private final ToysRepository toysRepository;

    private List<Dog> dogs;

    private List<Person> persons;

    private List<Toy> toys;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        dogs = List.of(
                Dog.builder()
                        .name("Terry")
                        .born(LocalDateTime.of(2015, 5, 1, 17, 50))
                        .dogBreed(DogBreed.BICHON)
                        .build(),
                Dog.builder()
                        .name("Bobby")
                        .born(LocalDateTime.of(2011, 1, 9, 20, 50))
                        .dogBreed(DogBreed.POODLE)
                        .build(),
                Dog.builder()
                        .name("Rammstein")
                        .born(LocalDateTime.of(2008, 1, 2, 16, 16))
                        .dogBreed(DogBreed.BULLDOG)
                        .build()
        );

        persons = List.of(
                Person.builder()
                        .name("Petr")
                        .build(),
                Person.builder()
                        .name("Marie")
                        .build()
        );

        toys = List.of(
                Toy.builder()
                        .name("Blanket")
                        .build(),
                Toy.builder()
                        .name("Bone")
                        .build(),
                Toy.builder()
                        .name("Ball")
                        .build()
        );


        dogRepository.saveAll(dogs);
        personRepository.saveAll(persons);
        toysRepository.saveAll(toys);


    }

}
