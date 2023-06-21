package ru.sber.repositories;

import java.math.BigDecimal;

/*
 * Интерфейс база данных для сохранения истории переводов
 */
public interface TranslationHistoryRepository {
    void addTranslationHistory(String massage, BigDecimal bigDecimal);
}
