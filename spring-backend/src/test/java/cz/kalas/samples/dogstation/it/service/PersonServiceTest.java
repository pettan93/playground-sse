package cz.kalas.samples.dogstation.it.service;

import cz.kalas.samples.dogstation.model.entity.Dog;
import cz.kalas.samples.dogstation.model.entity.Person;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import cz.kalas.samples.dogstation.service.PersonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;



@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;


    @Test
    public void savePersonTest() {

        var savedPerson = personService.save(new Person("Petr"));

        Assert.assertNotNull(savedPerson);
        Assert.assertEquals("Petr", savedPerson.getName());
    }

    @Test
    public void releaseSomeDogTest() {

        Person person = new Person("Petr");

        Dog dog1 = new Dog("Bobby");
        Dog dog2 = new Dog("Tim");

        person.setOwnedDogs(new ArrayList<>(List.of(dog1, dog2)));

        Person expectedPerson = new Person("Petr");
        expectedPerson.setOwnedDogs(new ArrayList<>(List.of(dog1)));

        var returnedPerson = personService.releaseSomeDog(person);

        Assert.assertNotNull(returnedPerson);
        Assert.assertEquals(expectedPerson.getOwnedDogs().size(),returnedPerson.getOwnedDogs().size());
    }


}
