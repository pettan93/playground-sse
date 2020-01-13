package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Dog;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("sql-server")
public interface DogRepositoryJPA extends JpaRepository<Dog, Integer>, DogRepository {

}

