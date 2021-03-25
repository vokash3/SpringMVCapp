package ru.wain.springmvcapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wain.springmvcapp.DAO.PersonDAO;
import ru.wain.springmvcapp.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDAO.index()); // returns List<Person>
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){ //@PathVariable соотносит параметр из мапинга "/{id}" с принимаемым аргументом метода int id
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    /*
    Логика работы newPerson и create:
    1) Переход в браузере на /new
    2) передача пустого объекта Person в представление new.html
    3) Ввод значения в форме браузера - submit
    4) Передача POST методом в create обновлённого объекта Person с введённым значением поля name
    5) Сохранение объекта в базу в create через класс взаимодействия с БД.
    6) Перенаправление в create на /people (то есть отрабатывает метод index)
     */
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person()); // Передаём объект в представление, где будет форма для заполнения полей объекта
        return "/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person){ //Чтобы создать объект класса Person и в него положить данные из формы используется @ModelAttribute
        //Этот объект мы положим в БД
        personDAO.save(person);

        return "redirect:/people"; // После добавления объекта браузер будет перенаправлен на /people


    }
}
