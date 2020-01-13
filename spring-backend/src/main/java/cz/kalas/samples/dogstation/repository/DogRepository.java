package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Dog;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository {

//    public void saveAll(List<Dog> dogs);

    public Dog save(Dog dog);

    public List<Dog> findAll();

    public void deleteAll();

}

