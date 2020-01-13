package cz.kalas.samples.dogstation.component;

import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.model.DogBreed;
import cz.kalas.samples.dogstation.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInicializer {

    private final BaseRepository<Dog> dogRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        dogRepository.save(Dog.builder()
                .name("Terry")
                .born(LocalDateTime.of(2015, 5, 1, 17, 50))
                .dogBreed(DogBreed.BICHON)
                .build());

//        baseRepository.saveAll(List.of(
//                Dog.builder()
//                        .name("Terry")
//                        .born(LocalDateTime.of(2015, 5, 1, 17, 50))
//                        .dogBreed(DogBreed.BICHON)
//                        .build(),
//                Dog.builder()
//                        .name("Bobby")
//                        .born(LocalDateTime.of(2011, 1, 9, 20, 50))
//                        .dogBreed(DogBreed.POODLE)
//                        .build(),
//                Dog.builder()
//                        .name("Rammstein")
//                        .born(LocalDateTime.of(2008, 1, 2, 16, 16))
//                        .dogBreed(DogBreed.BULLDOG)
//                        .build()
//        ));


    }

}
