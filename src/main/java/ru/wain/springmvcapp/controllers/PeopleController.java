package ru.wain.springmvcapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wain.springmvcapp.DAO.PersonDAO;
import ru.wain.springmvcapp.models.Person;

import javax.validation.Valid;

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
    public String newPerson(@ModelAttribute("person") Person person){ //Так как GET запрос не передаёт данных (в данном случае), то @ModelAttribute создаст пустой объект класса Person. Это альтернатива использования Model model
//        model.addAttribute("person", new Person()); // Передаём объект в представление, где будет форма для заполнения полей объекта
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){ //Чтобы создать объект класса Person и в него положить данные из формы используется @ModelAttribute
        //Этот объект мы положим в БД
        //@Valid активирует валидаторы полей из класса Person
        //BindingResult позволяет поймать ошибку во время валидации. Обязательно стоит следующим параметром в сигнатуре метода после валидированного объекта
        if (bindingResult.hasErrors()){
            return "people/new"; //В форму с ошибками
        }
        personDAO.save(person);

        return "redirect:/people"; // После добавления объекта браузер будет перенаправлен на /people
    }

    //Метод для редактирования записи человека. Псевдо PATCH (так как в HTML5 существует только 2 метода GET и POST
    //PATCH,UPDATE,DELETE и тд достигаются путём взаимодействия со скрытым полем методов HTML _method
    //Запросы проходят через фильтр спринга, где он уже определяет истинный тип запроса

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    //Для обработки экзотических методов необходимо настроить фильтр Spring
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
