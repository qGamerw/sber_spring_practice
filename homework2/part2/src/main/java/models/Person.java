package models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class Person {
    private String name;
    private final Cat cat;

    private final Parrot parrot1;
    private final Parrot parrot2;
    private final Dog dog;

    @Autowired
    public Person(Cat cat, @Qualifier("getParrot1") Parrot parrot1, Parrot parrot2, Dog dog){
        this.cat = cat;
        this.parrot1 = parrot1;
        this.parrot2 = parrot2;
        this.dog = dog;
    }

    @PostConstruct
    public void init() {
        this.name = "Master";
        this.cat.setName("Murky");
        this.parrot1.setName("Koko");
        this.parrot2.setName("Kimi");
        this.dog.setName("Bobi");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimals() {
        return "\n\t" + cat + ", \n\t" + parrot1 + ", \n\t" + parrot2 + ", \n\t" + dog;
    }
}
