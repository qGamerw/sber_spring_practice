package ru.sber.services;

import ru.sber.model.Poem;

import java.util.List;
import java.util.logging.Logger;

/**
 * Интерфейс для взаимодействия класса с объектом поэзия
 */
public interface InteractionPoemInterfaceService {
    /**
     * Передает комментарий с анонимным автором
     *
     * @param text
     */
    void publishComment(String text);

    /**
     * Редактирует текст у автора
     *
     * @param poem
     * @param text
     */
    Poem editPoemText(Poem poem, String text);

    /**
     * Передает имя автора, текст и дату создания поэмы
     *
     * @param poem
     */
    String printInfoOfPoem(Poem poem);

    /**
     * Печатает список авторов
     *
     * @param authors
     */
    void printPublicAuthors(List<String> authors);

    /**
     * Устанавливает значение для поля логов
     *
     * @param logger
     */
    void setLogger(Logger logger);
}
