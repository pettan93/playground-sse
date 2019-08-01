package cz.kalas.samples.dogstation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode
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

