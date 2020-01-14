package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.model.Toy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Integer> {

    @Query("SELECT d FROM Dog d" +
            " JOIN FETCH d.ownedToys t")
    List<Dog> fetchAll();


}

