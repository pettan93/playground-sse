package cz.kalas.samples.dogstation.component;

import cz.kalas.samples.dogstation.model.entity.Dog;
import cz.kalas.samples.dogstation.model.entity.DogBreed;
import cz.kalas.samples.dogstation.model.entity.Person;
import cz.kalas.samples.dogstation.model.entity.Toy;
import cz.kalas.samples.dogstation.repository.DogRepository;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import cz.kalas.samples.dogstation.repository.ToysRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class DataInicializer {

    private final DogRepository dogRepository;

    private final PersonRepository personRepository;

    private final ToysRepository toysRepository;

    private List<Dog> dogs;

    private List<Person> persons;

    private List<Toy> toys;

    private static Integer DUMMY_N = 100;

    private static Integer NUMBER_OF_DOGS_PER_PERSON = 10;

    static class RandomNameGenerator {

        ClassPathResource file = new ClassPathResource("names.csv");

        private Set<String> usedNames = new HashSet<>();

        private List<String> names = new LinkedList<>();

        Random rand = new Random();

        public RandomNameGenerator() {
            Scanner scanner = null;
            try {
                scanner = new Scanner(file.getFile());
                while (scanner.hasNextLine()) {
                    names.add(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getRandom() {
            String randomName = names.get(rand.nextInt(names.size()));
            randomName = incrementIfNessesary(randomName);
            usedNames.add(randomName);
            return randomName;
        }

        private String incrementIfNessesary(String name) {
            int i = 0;
            while (usedNames.contains(name)) {
                i++;
                name += i;
            }
            return name;

        }

    }

    private RandomNameGenerator randomNameGenerator = new RandomNameGenerator();

    @EventListener
    public void appReady(ApplicationReadyEvent event) {


        var y = NUMBER_OF_DOGS_PER_PERSON;
        dogs = generateDogs(DUMMY_N * y);

        persons = generatePersons(DUMMY_N);

        toys = generateToys(DUMMY_N);

        for (Dog dog : dogs) {
            dog.setOwnedToys(getRandomSublist(toys)
            );
        }


        // Surjective function for assigning dogs to owners
        // n = n-times more dogs than persons
        // x - index of person in person array
        // y = index of dog in dogs array for asigning to person x
        // y = n.x + n


        for (Person person : persons) {
            for (int j = 0; j < (y - 1); j++) {

                if (person.getOwnedDogs() == null) {
                    person.setOwnedDogs(new ArrayList<>());
                }

                person.getOwnedDogs()
                        .add(dogs.get((persons.indexOf(person) * y) + j));
            }


        }

        personRepository.saveAll(persons);

        log.info("DataInicializer - Done");
    }


    private List<Dog> generateDogs(Integer n) {
        var dogs = new ArrayList<Dog>();
        for (int i = 0; i < n; i++) {
            dogs.add(Dog.builder()
                    .name(randomNameGenerator.getRandom())
                    .dogBreed(DogBreed.values()[new Random().nextInt(DogBreed.values().length)])
                    .born(LocalDateTime.now())
                    .build());
        }
        return dogs;
    }

    private List<Person> generatePersons(Integer n) {
        var persons = new ArrayList<Person>();
        for (int i = 0; i < n; i++) {
            persons.add(Person.builder()
                    .name(randomNameGenerator.getRandom())
                    .build());
        }
        return persons;
    }

    private List<Toy> generateToys(Integer n) {
        var toys = new ArrayList<Toy>();
        for (int i = 0; i < n; i++) {
            toys.add(Toy.builder()
                    .name(randomNameGenerator.getRandom())
                    .build());
        }
        return toys;
    }

    <T> List<T> getRandomSublist(List<T> source) {
        Random rand = new Random();
        var start = rand.nextInt(source.size());
        var end = rand.nextInt(source.size());
        while (start > end) {
            end = rand.nextInt(source.size());
        }
        return source.subList(start, end);
    }

}
