package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.aspects.NotEmpty;
import ru.sber.model.Poem;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Класс для взаимодействия с объектом поэзия
 */
@Service
public class InteractionPoemService {

    private Logger logger = Logger.getLogger(InteractionPoemService.class.getName());

    public void publishComment(String text) {
        logger.info("Method: Publishing comment: " + text + " author: null");
    }

    @NotEmpty
    public Poem editText(Poem poem, String text) {
        logger.info("Method: Editing comment: " + text);
        poem.setText(text);
        return poem;
    }

    @NotEmpty
    public String printPoem(Poem poem) {
        logger.info("Method: Author poem: " + poem.getAuthor() + ",  text poem: " + poem.getText());
        return "Method: Author poem: " + poem.getAuthor() + ",  text poem: " + poem.getText();
    }

    @NotEmpty
    public void publishArrayText(ArrayList<String> textArr) {
        logger.info("Method: Editing author: " + textArr);
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
