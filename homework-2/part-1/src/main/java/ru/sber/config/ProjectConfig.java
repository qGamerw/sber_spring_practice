package ru.sber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sber.model.Cat;
import ru.sber.model.Dog;
import ru.sber.model.Parrot;
import ru.sber.model.Person;

/**
 * Класс для создания конфигурации проекта
 */
@Configuration
public class ProjectConfig {

    @Bean
    public Person person() {
        return new Person(getParrot1(), getParrot2(), getDog(), getCat());
    }

    @Bean
    public Parrot getParrot1() {
        return new Parrot("Koko");
    }

    @Bean
    public Parrot getParrot2() {
        return new Parrot("Kiki");
    }

    @Bean
    public Dog getDog() {
        return new Dog("Bobi");
    }

    @Bean
    public Cat getCat() {
        return new Cat("Murky");
    }
}
