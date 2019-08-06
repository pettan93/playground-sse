package cz.kalas.samples.dogstation.dog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j
public class DogService {

    private final DogRepository dogRepository;

    private final RandomNameGenerator randomNameGenerator = new RandomNameGenerator(100);

    private final Integer MIN_DELAY_TIME = 4000;
    private final Integer MAX_DELAY_TIME = 5000;

    private final Lock lock = new ReentrantLock();
    private Boolean delayedDogInProgress = false;

    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    public void deleteAll() {
        dogRepository.deleteAll();
    }

    public Boolean isDogInProgress() {
        return delayedDogInProgress;
    }

    @Async
    public CompletableFuture<Dog> createDelayedDog(String name) {

        try {
            lock.tryLock(MAX_DELAY_TIME, TimeUnit.MILLISECONDS);

            delayedDogInProgress = true;
            Thread.sleep(new Random().nextInt(MAX_DELAY_TIME - MIN_DELAY_TIME + 1) + MIN_DELAY_TIME);
            delayedDogInProgress = false;

        } catch (InterruptedException e) {
            e.printStackTrace();
            delayedDogInProgress = false;
            lock.unlock();
            return CompletableFuture.failedFuture(e);
        }

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
