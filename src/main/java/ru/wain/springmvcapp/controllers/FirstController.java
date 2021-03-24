package ru.wain.springmvcapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller

@RequestMapping("/first")
public class FirstController {

//    @GetMapping("/hello")
//    public String helloPage(HttpServletRequest request) { // Можно отправлять гет запрос без параметров
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//
//        System.out.println("Hello " + name + " " + surname);
//        return "first/hello";
//    }

    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname,
                            Model model) { // В этом случае (реквест парам) передавать значения обязательно(если не использовать параметр required = false, true - по умолчанию)
//        System.out.println("Hello " + name + " " + surname);
        model.addAttribute("message", "Hello " + name + " " + surname); //Через Model можно передавать данные во View (Thymeleaf в данном случае)
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodbyePage() {
        return "first/goodbye";
    }

    @GetMapping("/calculator")
    public String calculate(@RequestParam(value = "a") int a, @RequestParam(value = "b") int b,
                            @RequestParam(value = "mode") String mode, Model model){
        double result;
        try {
            switch (mode) {
                case "multi":
                    result = a * b;
                    break;
                case "addition":
                    result = a + b;
                    break;
                case "subtraction":
                    result = a - b;
                    break;
                case "division":
                    result = a / b;
                    break;
                default:
                    result = 123.333d;
                    break;
            }

            model.addAttribute("result", result);
            return "first/calculator";
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "first/error";
        }

    }
}
