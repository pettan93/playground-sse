package cz.kalas.samples.dogstation.model.dto;

import cz.kalas.samples.dogstation.model.entity.DogBreed;
import cz.kalas.samples.dogstation.model.entity.Toy;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DogDto {

    private Integer id;

    private String name;

    private DogBreed dogBreed;

    private LocalDateTime born;

    private List<ToyDto> ownedToys;



}
