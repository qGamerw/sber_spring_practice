package ru.sber.services;

import ru.sber.model.Poem;

import java.util.List;
import java.util.logging.Logger;

/**
 * Интерфейс для взаимодействия класса с объектом поэзия
 */
public interface InteractionPoemInterfaceService {
    /**
     * Передать комментарий с анонимным автором
     */
    void publishComment(String text);

    /**
     * Редактируем строку у автора
     */
    Poem editPoemText(Poem poem, String text);

    /**
     * Передать информации о поэме
     */
    String printInfoOfPoem(Poem poem);

    /**
     * Передать список авторов
     */
    void printPublicAuthors(List<String> authors);

    /**
     * Установить значение для поля логов
     */
    void setLogger(Logger logger);
}
