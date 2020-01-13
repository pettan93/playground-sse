package cz.kalas.samples.dogstation.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Dog {


    private String name;

    private DogBreed dogBreed;

    private LocalDateTime born;


}

