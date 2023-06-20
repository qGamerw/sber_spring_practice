package config;

import models.Cat;
import models.Dog;
import models.Parrot;
import models.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public Person person() {
        var person = new Person(parrot1(), parrot2(), dog(), cat());
        return person;
    }
    @Bean
    public Parrot parrot1() {
        var p = new Parrot();
        p.setName("Koko");
        return p;
    }

    @Bean
    public Parrot parrot2() {
        var parrot = new Parrot();
        parrot.setName("Kiko");
        return parrot;
    }

    @Bean
    public Dog dog() {
        var dog = new Dog();
        dog.setName("Bobi");
        return dog;
    }

    @Bean
    public Cat cat() {
        var cat = new Cat();
        cat.setName("Murky");
        return cat;
    }
}
