package cz.kalas.samples.dogstation.repository.dog;

import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.repository.BaseRepository;
import cz.kalas.samples.dogstation.repository.RepositoryNaive;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("in-memory")
public class DogRepositoryNaive extends RepositoryNaive<Dog> implements BaseRepository<Dog> {


}