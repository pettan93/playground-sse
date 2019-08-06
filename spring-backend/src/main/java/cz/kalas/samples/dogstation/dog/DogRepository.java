package cz.kalas.samples.dogstation.dog;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DogRepository {

    private static List<Dog> dogBox = new ArrayList<>();

    static {
        dogBox.addAll(
                Arrays.asList(
                        new Dog("Terry", Dog.DogBreed.BICHON, LocalDateTime.of(2015, 5, 1, 17, 50)),
                        new Dog("Bobby", Dog.DogBreed.POODLE, LocalDateTime.of(2011, 1, 9, 20, 50)),
                        new Dog("Rammstein", Dog.DogBreed.BULLDOG, LocalDateTime.of(2008, 1, 2, 16, 16))
                )
        );
    }


    public Dog save(Dog dog) {
        dogBox.add(dog);
        return dog;
    }

    public List<Dog> findAll() {
        return dogBox;
    }

    public void deleteAll(){
        dogBox.removeAll(dogBox);
    }

}
