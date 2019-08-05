package cz.kalas.samples.dogstation.dog;

import cz.kalas.samples.dogstation.sseNotify.Notification;
import cz.kalas.samples.dogstation.sseNotify.NotifyType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping(value = "/dog")
@AllArgsConstructor
@Slf4j
public class DogEndpoint {

    private final DogService dogService;

    private List<SseEmitter> sseEmitters;

    @GetMapping("/list")
    public List<Dog> getAll() {
        log.debug("Get them all");
        return dogService.getAllDogs();
    }

    /**
     * SSE
     */
    @PostMapping("/create/delayed")
    public Notification delayedNewDog() throws InterruptedException {
        log.debug("Let's make delayed dog");

        var randomName = dogService.getRandomDogName();
        var futureDog = dogService.createDelayedDog(randomName);

        futureDog.thenAccept(dog -> {
            log.debug("Dog {} is ready!", dog.getName());

            for (SseEmitter emitter : sseEmitters) {
                try {
                    emitter.send(new Notification(
                            "Delayed dog " + randomName + " has just been born!",
                            NotifyType.TRIGGER));

                } catch (Exception ex) {
                    emitter.completeWithError(ex);
                }
            }
        });

        return new Notification(
                "Delayed dog " + randomName + " is being born..",
                NotifyType.PLAIN_TEXT);
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

    @GetMapping("/sse/subscribe")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter(0L);
        try {
            emitter.send(new Notification(
                    "Subscribed to newborn notifications.",
                    NotifyType.PLAIN_TEXT));
        } catch (Exception ex) {
            emitter.completeWithError(ex);
        }
        sseEmitters.add(emitter);
        return emitter;
    }

}

