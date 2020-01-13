package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Dog;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("in-memory")
public class DogRepositoryNaive implements DogRepository {

    private static List<Dog> dogBox = new ArrayList<>();

    public void saveAll(List<Dog> dogs) {
        dogBox.addAll(dogs);
    }

    public Dog save(Dog dog) {
        dogBox.add(dog);
        return dog;
    }

    public List<Dog> findAll() {
        return dogBox;
    }

    public void deleteAll() {
        dogBox.removeAll(dogBox);
    }

}