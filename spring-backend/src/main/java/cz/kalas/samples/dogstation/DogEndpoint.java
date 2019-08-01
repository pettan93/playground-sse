package cz.kalas.samples.dogstation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class DogEndpoint {

    private final DogService dogService;

    @GetMapping("/dogs")
    public List<Dog> getAll() {
        return dogService.getAllDogs();
    }

    @PostMapping("/create/instant")
    public Dog instantNewDog() {
        return dogService.createRandomDog();
    }


}
