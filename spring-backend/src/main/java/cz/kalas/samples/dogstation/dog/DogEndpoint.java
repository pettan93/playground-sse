package cz.kalas.samples.dogstation.dog;

import cz.kalas.samples.dogstation.sseNotify.Notification;
import cz.kalas.samples.dogstation.sseNotify.NotifyType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.time.Duration;
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

    /**
     * SSE
     */
    @PostMapping("/create/delayed")
    public Notification delayedNewDog() throws InterruptedException {
        log.debug("Let's make delayed dog");

        var randomName = dogService.getRandomDogName();
        var futureDog = dogService.createDelayedDog(randomName);

        futureDog
                .thenAccept(dog -> {
                    log.debug("Dog {} is ready!", dog.getName());
                    sseEmitters.forEach(emitter -> {
                        try {
                            emitter.send(new Notification(
                                    "Delayed dog " + randomName + " has just been born!",
                                    NotifyType.TRIGGER));
                        } catch (Exception ex) {
                            emitter.completeWithError(ex);
                        }
                    });
                })
                .exceptionally(t -> {
                    sseEmitters.forEach(emitter -> {
                        try {
                            emitter.send(new Notification(
                                    "Delayed dog " + randomName + " has just been cancelled :(",
                                    NotifyType.PLAIN_TEXT));
                        } catch (Exception ex) {
                            emitter.completeWithError(ex);
                        }
                    });
                    t.printStackTrace();
                    return null;
                });

        return new Notification(
                "Delayed dog " + randomName + " is being born..",
                NotifyType.PLAIN_TEXT);
    }

    @GetMapping("/notification/sse")
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

    @GetMapping(path = "/notification/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notification> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence ->
                        new Notification(
                                String.valueOf(dogService.isDogInProgress()),
                                NotifyType.STATUS));
    }

}

