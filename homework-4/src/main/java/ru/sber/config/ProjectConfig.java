package ru.sber.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Класс для создания конфигурации проекта
 */
@Configuration
@ComponentScan(basePackages = {"ru.sber.services", "ru.sber.aspects"})
@EnableAspectJAutoProxy
public class ProjectConfig {
}
