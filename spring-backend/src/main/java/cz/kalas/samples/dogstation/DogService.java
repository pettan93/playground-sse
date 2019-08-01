package cz.kalas.samples.dogstation;

import lombok.AllArgsConstructor;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class DogService {

    private DogRepository dogRepository;

    private final RandomNameGenerator randomNameGenerator = new RandomNameGenerator(100);

    private final Integer MIN_BIRTH_TIME = 2000;
    private final Integer MAX_BIRTH_TIME = 10000;

    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    public void deleteAll(){
        dogRepository.deleteAll();
    }


    public Dog waitForBirth() throws InterruptedException {
        Thread.sleep(new Random().nextInt(MAX_BIRTH_TIME - MIN_BIRTH_TIME + 1) + MIN_BIRTH_TIME);
        return createRandomDog();
    }

    public Dog createRandomDog() {
        var randomName = randomNameGenerator.next();
        return dogRepository.save(new Dog(
                capitalizeFirstLetter(randomName.substring(0, randomName.indexOf("_"))),
                Dog.DogBreed.values()[new Random().nextInt(Dog.DogBreed.values().length)],
                LocalDateTime.now()
        ));
    }


    private String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }


}
