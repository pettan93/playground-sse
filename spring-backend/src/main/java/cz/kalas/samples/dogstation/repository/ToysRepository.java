package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Person;
import cz.kalas.samples.dogstation.model.Toy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToysRepository extends JpaRepository<Toy, Integer> {



}

