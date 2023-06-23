package ru.sber.model;

/**
 * Интерфейс для реализации классов животных
 */
public interface animalInterface {
    /**
     * Возвращает имя животного
     *
     * @return имя животного
     */
    String getName();

    /**
     * Устанавливает имя животного
     *
     * @param name имя животного
     */
    void setName(String name);
}