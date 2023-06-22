package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.model.Poem;
import ru.sber.services.InteractionPoemService;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var service = context.getBean(InteractionPoemService.class);
        var poem = new Poem("Natasha", "Demo text");

        try {
            service.printPoem(poem);
            service.editText(poem, null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            service.editText(poem, "");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            service.editText(poem, "sdf");
            service.publishArrayText(new ArrayList<String>());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            service.publishArrayText(new ArrayList<String>());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}