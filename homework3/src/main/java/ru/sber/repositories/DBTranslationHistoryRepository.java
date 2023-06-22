package ru.sber.repositories;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * База данных для сохранения истории переводов
 */
@Repository
public class DBTranslationHistoryRepository implements TranslationHistoryRepository {

    private static DBTranslationHistoryRepository instance;
    private final List<String> translationHistory = new ArrayList<String>();

    private DBTranslationHistoryRepository() {
    }

    public static DBTranslationHistoryRepository getInstance() {
        if (instance == null) {
            instance = new DBTranslationHistoryRepository();
        }
        return instance;
    }

    @Override
    public void addTranslationHistory(String massage, BigDecimal bigDecimal) {
        translationHistory.add("Number: " + massage + ", sum: " + bigDecimal + ", date: " + LocalDate.now());
    }

    public List<String> getTranslationHistory() {
        return translationHistory;
    }
}
