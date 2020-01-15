package cz.kalas.samples.dogstation.model.dto;

import cz.kalas.samples.dogstation.model.entity.Dog;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

public class PersonDto {

    private Integer id;

    private String name;

    private List<DogDto> ownedDogs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DogDto> getOwnedDogs() {
        return ownedDogs;
    }

    public void setOwnedDogs(List<DogDto> ownedDogs) {
        this.ownedDogs = ownedDogs;
    }
}
