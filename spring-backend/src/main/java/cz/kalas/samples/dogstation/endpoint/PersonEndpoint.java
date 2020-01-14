package cz.kalas.samples.dogstation.endpoint;

import cz.kalas.samples.dogstation.events.StateChangeEvent;
import cz.kalas.samples.dogstation.events.StateChangeEventPublisher;
import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.model.DogStationState;
import cz.kalas.samples.dogstation.model.Person;
import cz.kalas.samples.dogstation.model.notifications.Notification;
import cz.kalas.samples.dogstation.model.notifications.NotifyType;
import cz.kalas.samples.dogstation.service.DogService;
import cz.kalas.samples.dogstation.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.MetaData;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class PersonEndpoint {

    private final PersonService personService;


    @GetMapping(value = "/person/getRandom")
    public ResponseEntity<Person> getRandom() {
        log.debug("Get random");

        var randomPerson = personService.getRandom();

        return randomPerson.isPresent() ?
                ResponseEntity.of(randomPerson) : ResponseEntity.badRequest().build();
    }


}

