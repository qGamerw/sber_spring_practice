package ru.sber;

import config.ProjectConfig;
import models.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var person = context.getBean(Person.class);

        System.out.println("Human name: \n\t{ " + person.getName() + " }");
        System.out.println("Animal names in humans: " + person.getAnimals());
    }
}