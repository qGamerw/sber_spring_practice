package ru.sber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.sber.aspects.LoggingAspect;

/**
 * Класс создания конфигурации проекта
 */
@Configuration
@ComponentScan(basePackages = {"ru.sber.services"})
@EnableAspectJAutoProxy
public class ProjectConfig {
    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

}
