package ru.sber.repositories;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DBTranslationHistoryRepository implements TranslationHistoryRepository {

    private List<String> translationHistory = new ArrayList<String>();

    @Override
    public void addTranslationHistory(String massage, BigDecimal bigDecimal) {
        translationHistory.add("Number " + massage + " sum " + bigDecimal);
    }

    public List<String> getTranslationHistory() {
        return translationHistory;
    }
}
