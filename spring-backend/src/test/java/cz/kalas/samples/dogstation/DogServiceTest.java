package cz.kalas.samples.dogstation;

import cz.kalas.samples.dogstation.dog.DogRepository;
import cz.kalas.samples.dogstation.dog.DogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DogServiceTest {


    private DogService dogService = new DogService(new DogRepository());

    @Test
    public void createRandomDogTest() {

        var firstDog = dogService.createRandomDog();
        var secondDog = dogService.createRandomDog();

        Assert.assertNotNull(firstDog);
        Assert.assertNotNull(secondDog);
        Assert.assertNotEquals(firstDog, secondDog);

    }


}
