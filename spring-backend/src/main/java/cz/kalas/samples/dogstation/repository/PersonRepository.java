package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT p.id FROM Person p")
    List<Integer> getIds();

    @Query("SELECT p FROM Person p" +
            " JOIN FETCH p.ownedDogs d")
    List<Person> getAll();


}

