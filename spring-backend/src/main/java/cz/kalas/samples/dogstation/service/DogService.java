package cz.kalas.samples.dogstation.service;

import cz.kalas.samples.dogstation.events.StateChangeEvent;
import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.model.DogBreed;
import cz.kalas.samples.dogstation.model.DogStationState;
import cz.kalas.samples.dogstation.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j
public class DogService {

    private final BaseRepository<Dog> baseRepository;

    private final ApplicationEventPublisher publisher;

    private final RandomNameGenerator randomNameGenerator = new RandomNameGenerator(100);

    private final Integer MIN_DELAY_TIME = 4000;
    private final Integer MAX_DELAY_TIME = 5000;

    private final Lock lock = new ReentrantLock();

    public List<Dog> getAllDogs() {
        return baseRepository.findAll();
    }

    public void deleteAll() {
        baseRepository.deleteAll();
    }

    @Async
    public CompletableFuture<Dog> createDelayedDog(String name) {

        lock.lock();
        log.debug("locked");
        try {
            publisher.publishEvent(new StateChangeEvent(this, DogStationState.WORKING));

            Thread.sleep(new Random().nextInt(MAX_DELAY_TIME - MIN_DELAY_TIME + 1) + MIN_DELAY_TIME);

            publisher.publishEvent(new StateChangeEvent(this, DogStationState.IDLE));

            log.debug("unlocked");
            lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
            publisher.publishEvent(new StateChangeEvent(this, DogStationState.IDLE));
            lock.unlock();
            return CompletableFuture.failedFuture(e);
        }

        return CompletableFuture.completedFuture(createRandomDog(name));
    }

    public Dog createRandomDog() {
        return createRandomDog(getRandomDogName());
    }

    public Dog createRandomDog(String name) {
        return baseRepository.save(
                Dog.builder()
                        .name(name)
                        .born(LocalDateTime.now())
                        .dogBreed(DogBreed.values()[new Random().nextInt(DogBreed.values().length)])
                        .build()
        );
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
