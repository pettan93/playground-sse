package cz.kalas.samples.dogstation.component;

import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.model.DogBreed;
import cz.kalas.samples.dogstation.model.Person;
import cz.kalas.samples.dogstation.model.Toy;
import cz.kalas.samples.dogstation.repository.DogRepository;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import cz.kalas.samples.dogstation.repository.ToysRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInicializer {

    private final DogRepository dogRepository;

    private final PersonRepository personRepository;

    private final ToysRepository toysRepository;

    private List<Dog> dogs;

    private List<Person> persons;

    private List<Toy> toys;

    private static Integer DUMMY_N = 1000;

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


        dogs = generateDogs(DUMMY_N);

        persons = generatePersons(DUMMY_N);

        toys = generateToys(DUMMY_N);

        for (Dog dog : dogs) {
            dog.setOwnedToys(getRandomSublist(toys)
            );
        }

        for (Person person : persons) {
            person.setOwnedDogs(dogs);
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
