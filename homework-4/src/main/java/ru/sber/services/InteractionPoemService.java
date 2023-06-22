package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.aspects.NotEmpty;
import ru.sber.model.Poem;

import java.util.List;
import java.util.logging.Logger;

/**
 * Класс для взаимодействия с объектом поэмой
 */
@Service
public class InteractionPoemService implements InteractionPoemInterfaceService {

    private Logger logger = Logger.getLogger(InteractionPoemService.class.getName());

    @Override
    public void publishComment(String text) {
        logger.info("Method: Publishing comment: " + text + " author: null");
    }

    @Override
    @NotEmpty
    public Poem editPoemText(Poem poem, String text) {
        logger.info("Method: Editing comment: " + text);
        poem.setText(text);
        return poem;
    }

    @Override
    @NotEmpty
    public String printInfoOfPoem(Poem poem) {
        logger.info("Method: Author poem: " + poem.getAuthor() + ", text poem: " + poem.getText() + ", date: " + poem.getCreatedDate());
        return "Method: Author poem: " + poem.getAuthor() + ", text poem: " + poem.getText() + ", date: " + poem.getCreatedDate();
    }

    @Override
    @NotEmpty
    public void printPublicAuthors(List<String> authors) {
        logger.info("Method: Print public author: " + authors);
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
