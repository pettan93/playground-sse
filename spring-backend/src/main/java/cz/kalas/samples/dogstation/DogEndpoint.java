package cz.kalas.samples.dogstation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

