package ru.wain.springmvcapp.DAO;

import org.springframework.jdbc.core.RowMapper;
import ru.wain.springmvcapp.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * Предназначен для получения ResultSet и соответсвующих значений из БД
 * Позволяет не засорять DAO
 * Часть JDBS-template
 * Для ознакомления.
 * В библиотеке JDBC-tamplate есть BeanPropertyRowMapper(CLAZZ.class), который автоматом создаёт RowMapper для модели
 */
public class PersonMapper implements RowMapper<Person> { //Преоюразуем строки таблицы в поля класса Person
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {

        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));

        return person;
    }
}
