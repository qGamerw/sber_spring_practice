package ru.sber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sber.model.Cat;
import ru.sber.model.Dog;
import ru.sber.model.Parrot;
import ru.sber.model.Person;

@Configuration
public class ProjectConfig {

    @Bean
    public Person person() {
        var person = new Person(getParrot1(), getParrot2(), getDog(), getCat());
        return person;
    }

    @Bean
    public Parrot getParrot1() {
        var parrot = new Parrot();
        parrot.setName("Koko");
        return parrot;
    }

    @Bean
    public Parrot getParrot2() {
        var parrot = new Parrot();
        parrot.setName("Kiko");
        return parrot;
    }

    @Bean
    public Dog getDog() {
        var dog = new Dog();
        dog.setName("Bobi");
        return dog;
    }

    @Bean
    public Cat getCat() {
        var cat = new Cat();
        cat.setName("Murky");
        return cat;
    }
}
