package cz.kalas.samples.dogstation.model.dto;

import jdk.jfr.DataAmount;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ToyDto {

    private Integer id;

    private String name;


}
