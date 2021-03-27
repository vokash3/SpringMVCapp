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
        people.add(new Person(++PERSON_INDEX, "Tom", 12, "tom@cartoon.org"));
        people.add(new Person(++PERSON_INDEX, "Jerry", 11, "jerry@cartoon.org"));
        people.add(new Person(++PERSON_INDEX, "Spike", 14, "spike@cartoon.org"));
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

    public void update(int id, Person person) {
        Person personToUpdate = show(id);
        personToUpdate.setName(person.getName());
        personToUpdate.setAge(person.getAge());
        personToUpdate.setEmail(person.getEmail());
    }

    public void delete(int id) {
        people.removeIf(p -> p.getId() == id);
    }
}
