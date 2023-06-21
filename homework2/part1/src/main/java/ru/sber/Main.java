package ru.sber;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.models.Person;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var person = context.getBean(Person.class);

        System.out.println("Human name: \n\t{ " + person.getName() + " }");
        System.out.println("Animal names in humans: " + person.getAnimals());
    }
}