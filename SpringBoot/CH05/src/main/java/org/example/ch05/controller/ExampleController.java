package org.example.ch05.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {
    @GetMapping("/test")
    public String test(Model model) {

        Person person = new Person();
        person.setId(1L);
        person.setName("장유빈");
        person.setAge(12);
        person.setHobbies(List.of("운동","공예"));

        model.addAttribute("person", person);
        model.addAttribute("welcome", "안녕하세요");
        model.addAttribute("today", LocalDate.now());

        return "test"; // resource/template/test.html
    }


    @Setter
    @Getter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
