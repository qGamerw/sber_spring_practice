package ru.sber.repositories;

import java.math.BigDecimal;

public interface TranslationHistoryRepository {
    void addTranslationHistory(String massage, BigDecimal bigDecimal);
}
