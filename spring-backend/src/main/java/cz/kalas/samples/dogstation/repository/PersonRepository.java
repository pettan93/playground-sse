package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}

