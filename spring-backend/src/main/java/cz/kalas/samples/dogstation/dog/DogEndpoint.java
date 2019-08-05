package cz.kalas.samples.dogstation.dog;

import cz.kalas.samples.dogstation.sseNotify.Notification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/dog")
@AllArgsConstructor
@Slf4j
public class DogEndpoint {

    private final DogService dogService;

    @GetMapping("/list")
    public List<Dog> getAll() {
        log.debug("Get them all");
        return dogService.getAllDogs();
    }

    @PostMapping("/create/delayed")
    public Notification delayedNewDog() throws InterruptedException {
        log.debug("Let's make delayed dog");

        var randomName = dogService.getRandomDogName();
        var futureDog = dogService.createDelayedDog(randomName);

        futureDog.thenAccept(dog -> log.debug("Dog {} is ready!", dog.getName()));

        return new Notification(LocalDateTime.now().getSecond(), "Delayed dog " + randomName + " is being born..");
    }

    @PostMapping("/create/instant")
    public Dog instantNewDog() {
        log.debug("Let's make instant dog");
        return dogService.createRandomDog();
    }

    @PostMapping("/delete/all")
    public void deleteAll() {
        log.debug("Delete them all");
        dogService.deleteAll();
    }


}

