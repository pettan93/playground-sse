package cz.kalas.samples.dogstation.model.dto;

import cz.kalas.samples.dogstation.model.PersonState;
import lombok.Data;

import java.util.List;

@Data
public class PersonDto {

    private Integer id;

    private String name;

    private List<DogDto> ownedDogs;

    private PersonState personState;

}
