package cz.kalas.samples.dogstation.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_dog")
    private Integer id;

    private String name;

    private DogBreed dogBreed;

    private LocalDateTime born;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Toy> ownedToys;

    public Dog(String name) {
        this.name = name;
    }
}

