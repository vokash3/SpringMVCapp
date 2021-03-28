package ru.wain.springmvcapp.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.wain.springmvcapp.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component // Spring создаст бин этого класса и внедрит его в PeopleController
public class PersonDAO {

   private JdbcTemplate jdbcTemplate;

   @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        String SQL = "SELECT * FROM Person";
//       return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper()); // Второй индекс - RowMapper, который заполняет обёект инфой из БД.
        return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        String SQL = "SELECT * FROM Person WHERE id=?";
//        return jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)). //Deprecated
//                stream().findAny().orElse(null); //Возвращает List
        return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Person.class), id).
                stream().findAny().orElse(null); //Возвращает List
    }

    public void save(Person person) {
            String SQL = "INSERT INTO Person VALUES (1, ?, ?, ?)";
            jdbcTemplate.update(SQL, person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person person) {
        String SQL = "UPDATE Person SET name=?, age=?, email=? WHERE id=?";
       jdbcTemplate.update(SQL, person.getName(), person.getAge(), person.getEmail(), person.getId());
    }

    public void delete(int id) {
        String SQL = "DELETE FROM Person WHERE id=?";
        jdbcTemplate.update(SQL, id);
    }
}
