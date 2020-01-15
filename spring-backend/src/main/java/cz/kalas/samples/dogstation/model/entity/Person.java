package cz.kalas.samples.dogstation.model.entity;

import cz.kalas.samples.dogstation.model.PersonState;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_person")
    private Integer id;

    private String name;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Dog> ownedDogs;

    @Builder.Default
    private PersonState personState = PersonState.IDLE;

    public Person(String name) {
        this.name = name;
    }

}

