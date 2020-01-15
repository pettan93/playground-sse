package cz.kalas.samples.dogstation.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonDto {

    private Integer id;

    private String name;

    private List<DogDto> ownedDogs;

}
