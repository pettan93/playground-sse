package cz.kalas.samples.dogstation.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_dog")
    private Integer id;

    private String name;

    private DogBreed dogBreed;

    private LocalDateTime born;



}

