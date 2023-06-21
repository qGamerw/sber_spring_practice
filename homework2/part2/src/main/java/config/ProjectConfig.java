package config;

import models.Parrot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "models")
public class ProjectConfig {
    @Bean("getParrot1")
    public Parrot getParrot1() {
        return new Parrot();
    }

//    @Bean("getParrot2")
//    public Parrot getParrot2() {
//        return new Parrot();
//    }
}
