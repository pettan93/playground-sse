package cz.kalas.samples.dogstation.repository;

import cz.kalas.samples.dogstation.model.Dog;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class RepositoryNaive<T> {

    private List<T> db = new ArrayList<>();
//
//    List<T> saveAll(List<T> var1){
//         db.addAll(var1);
//         return null;
//    }

    public T save(T entity) {
        db.add(entity);
        return entity;
    }

    public List<T> findAll() {
        return db;
    }

    public void deleteAll() {
        db.removeAll(db);
    }

}