package ru.sber.repositories;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс для взаимодействия с базой данных сохранения истории переводов
 */
public interface TranslationHistoryRepository {
    /**
     * Сохраняет перевод в базу данных
     *
     * @param phone      телефон
     * @param bigDecimal сумма
     */
    void addTranslationHistory(String phone, BigDecimal bigDecimal);

    /**
     * Передает историю переводов номеров
     *
     * @return List<String> история переводов
     */
    List<String> getTranslationHistory();
}
