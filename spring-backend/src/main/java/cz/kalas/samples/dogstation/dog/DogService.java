package cz.kalas.samples.dogstation.dog;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Slf4j
public class DogService {

    private DogRepository dogRepository;

    private final RandomNameGenerator randomNameGenerator = new RandomNameGenerator(100);

    private final Integer MIN_DELAY_TIME = 4000;
    private final Integer MAX_DELAY_TIME = 5000;

    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    public void deleteAll() {
        dogRepository.deleteAll();
    }

    @Async
    public CompletableFuture<Dog> createDelayedDog(String name) throws InterruptedException {
        Thread.sleep(
                new Random().nextInt(MAX_DELAY_TIME - MIN_DELAY_TIME + 1) + MIN_DELAY_TIME);

        return CompletableFuture.completedFuture(createRandomDog(name));
    }

    public Dog createRandomDog() {
        return createRandomDog(getRandomDogName());
    }

    public Dog createRandomDog(String name) {
        return dogRepository.save(new Dog(
                name,
                Dog.DogBreed.values()[new Random().nextInt(Dog.DogBreed.values().length)],
                LocalDateTime.now()
        ));
    }

    public String getRandomDogName() {
        var randomName = randomNameGenerator.next();
        return capitalizeFirstLetter(randomName.substring(0, randomName.indexOf("_")));
    }

    private String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }


}
