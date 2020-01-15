package cz.kalas.samples.dogstation.endpoint;

import cz.kalas.samples.dogstation.model.dto.DogDto;
import cz.kalas.samples.dogstation.model.dto.PersonDto;
import cz.kalas.samples.dogstation.model.dto.ToyDto;
import cz.kalas.samples.dogstation.model.entity.Person;
import cz.kalas.samples.dogstation.model.entity.Toy;
import cz.kalas.samples.dogstation.service.DogService;
import cz.kalas.samples.dogstation.events.StateChangeEvent;
import cz.kalas.samples.dogstation.events.StateChangeEventPublisher;
import cz.kalas.samples.dogstation.model.entity.Dog;
import cz.kalas.samples.dogstation.model.entity.DogStationState;
import cz.kalas.samples.dogstation.model.notifications.Notification;
import cz.kalas.samples.dogstation.model.notifications.NotifyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DogEndpoint {


    private final DogService dogService;

    private List<SseEmitter> sseEmitters = new ArrayList<>();

    private final StateChangeEventPublisher stateChangeEventPublisher;

    private Flux<StateChangeEvent> flux;


    @PostConstruct
    public void init() {
        flux = Flux.create(stateChangeEventPublisher).share();
    }


    @GetMapping("")
    public String root() {
        log.debug("App root!");
        return "DogApp";
    }

    @GetMapping(value = "/dog/list")
    public List<Dog> getAll() {
        log.debug("Get them all");
        return dogService.getAllDogs();
    }

    @PostMapping("/dog/create/instant")
    public Dog instantNewDog() {
        log.debug("Let's make instant dog");
        return dogService.createRandomDog();
    }

    @PostMapping("/dog/delete/all")
    public void deleteAll() {
        log.debug("Delete them all");
        dogService.deleteAll();
    }

    /**
     * SSE ------------------------------------------
     */

    @GetMapping("/dog/notification/sse")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter(60000L); // timeout 15s
        emitter.onTimeout(() -> {
            emitter.complete();
            this.sseEmitters.remove(emitter);
        });
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

    @PostMapping("/dog/create/delayed")
    public Notification delayedNewDog() {
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


    @GetMapping(path = "/dog/notification/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DogStationState> streamFlux() {
        return flux.map(StateChangeEvent::getDogStationState);
    }

    @GetMapping(path = "/dog/info/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> streamFluxInterval() {
        log.debug("Flux subscribed!");
        return Flux.interval(Duration.ofSeconds(1))
                .map(l -> {
                    log.debug("Flux push " + l.intValue());
                    return l.intValue() + 1;
                });
    }

    public DogDto toDto(Dog dog) {

        return null;
    }

    public ToyDto toyDto(Toy toy) {
        return null;
    }

}

