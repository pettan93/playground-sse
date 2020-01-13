package cz.kalas.samples.dogstation.repository.dog;

import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.repository.BaseRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("sql-server")
public interface DogRepository extends BaseRepository<Dog>, JpaRepository<Dog, Integer> {


}

