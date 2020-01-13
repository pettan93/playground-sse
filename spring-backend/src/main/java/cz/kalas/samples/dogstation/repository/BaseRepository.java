package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Dog;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseRepository<T> {

//    List<T> saveAll(List<T> var1);

    T save(T dog);

    List<T> findAll();

    void deleteAll();

}

