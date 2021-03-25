package ru.wain.springmvcapp.DAO;

import org.springframework.stereotype.Component;
import ru.wain.springmvcapp.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component // Spring создаст бин этого класса и внедрит его в PeopleController
public class PersonDAO {

    private List<Person> people; // Временная замена БД (включая нестатический блок)
    private static int PERSON_INDEX;
    {
        people = new ArrayList<Person>();
        people.add(new Person(++PERSON_INDEX, "Tom"));
        people.add(new Person(++PERSON_INDEX, "Jerry"));
        people.add(new Person(++PERSON_INDEX, "Spike"));
        people.add(new Person(++PERSON_INDEX, "Yell"));
        people.add(new Person(++PERSON_INDEX, "Lucky"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PERSON_INDEX); //Так как id как бы динамически инкриментируется при добавлении в БД
        people.add(person);
    }
}
