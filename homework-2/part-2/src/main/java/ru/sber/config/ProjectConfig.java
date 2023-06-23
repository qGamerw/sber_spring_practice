package ru.sber.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Класс создания конфигурации проекта
 */
@Configuration
@ComponentScan(basePackages = "ru.sber.model")
public class ProjectConfig {
}
