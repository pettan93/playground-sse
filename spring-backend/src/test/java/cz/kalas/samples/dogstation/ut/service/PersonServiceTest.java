package cz.kalas.samples.dogstation.ut.service;

import cz.kalas.samples.dogstation.model.entity.Dog;
import cz.kalas.samples.dogstation.model.entity.Person;
import cz.kalas.samples.dogstation.repository.DogRepository;
import cz.kalas.samples.dogstation.repository.PersonRepository;
import cz.kalas.samples.dogstation.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    @Mock
    DogRepository dogRepository;

    PersonService personService;

    @Before
    public void init() {
        personService = new PersonService(personRepository,dogRepository);
    }

    @Test
    public void savePersonTest() {

        when(personRepository.save(any(Person.class))).thenReturn(new Person());

        personService.save(new Person("Petr"));

        // verify if the save method is called when createTodo is called too
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void releaseSomeDogTest() {

        Person person = new Person("Petr");

        Dog dog1 = new Dog("Bobby");
        Dog dog2 = new Dog("Tim");

        person.setOwnedDogs(new ArrayList<>(List.of(dog1, dog2)));

        Person expectedPerson = new Person("Petr");
        expectedPerson.setOwnedDogs(new ArrayList<>(List.of(dog1)));

        when(personService.save(person)).thenReturn(expectedPerson);

        var returnedPerson = personService.releaseSomeDog(person);

        Assert.assertNotNull(returnedPerson);
        Assert.assertEquals(expectedPerson.getOwnedDogs().size(),returnedPerson.getOwnedDogs().size());
    }


}
