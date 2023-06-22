package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.model.Poem;
import ru.sber.services.InteractionPoemInterfaceService;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var service = context.getBean(InteractionPoemInterfaceService.class);
        var poem = new Poem("Natasha", "Demo text");

        try {
            service.publishComment("Demo comment");
            service.editPoemText(poem, null);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

        try {
            service.editPoemText(poem, "");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

        try {
            service.printPublicAuthors(new ArrayList<String>());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }
}