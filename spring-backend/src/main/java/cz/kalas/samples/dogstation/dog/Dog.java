package cz.kalas.samples.dogstation.dog;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Dog {

    enum DogBreed {
        BULLDOG,
        POODLE,
        BICHON
    }

    private String name;

    private DogBreed dogBreed;

    private LocalDateTime born;


}

