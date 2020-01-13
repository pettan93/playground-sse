package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.model.DogBreed;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DogRepository {

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