package cz.kalas.samples.dogstation.it.service;

import cz.kalas.samples.dogstation.AppConfiguration;
import cz.kalas.samples.dogstation.model.Dog;
import cz.kalas.samples.dogstation.model.Person;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import cz.kalas.samples.dogstation.service.PersonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;



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
