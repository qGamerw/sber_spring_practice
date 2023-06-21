package ru.sber.repositories;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * База данных для сохранения истории переводов
 */
@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DBTranslationHistoryRepository implements TranslationHistoryRepository {

    private List<String> translationHistory = new ArrayList<String>();

    @Override
    public void addTranslationHistory(String massage, BigDecimal bigDecimal) {
        translationHistory.add("Number: " + massage + ", sum: " + bigDecimal + ", date: " + LocalDate.now());
    }

    public List<String> getTranslationHistory() {
        return translationHistory;
    }
}
