package cz.kalas.samples.dogstation.service;

import cz.kalas.samples.dogstation.CustomHeaderFilter;
import cz.kalas.samples.dogstation.repository.DogRepository;
import cz.kalas.samples.dogstation.service.DogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DogServiceTest {

    @Autowired
    private ApplicationEventPublisher publisher;

    private DogService dogService = new DogService(new DogRepository(),publisher);

    @Test
    public void createRandomDogTest() {

        var firstDog = dogService.createRandomDog();
        var secondDog = dogService.createRandomDog();

        Assert.assertNotNull(firstDog);
        Assert.assertNotNull(secondDog);
        Assert.assertNotEquals(firstDog, secondDog);

    }


}
