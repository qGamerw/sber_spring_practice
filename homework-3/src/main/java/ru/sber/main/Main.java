package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.exception.BankClientException;
import ru.sber.model.Client;
import ru.sber.services.TransferMoneyAppService;
import ru.sber.services.TransferMoneyInterfaceService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        try {
            var app = context.getBean(TransferMoneyInterfaceService.class);
            app.signInBankApp(new Client(1, "Oleg", "+7809", BigDecimal.valueOf(1000)));
            app.sendMoneyToPhone("+753", BigDecimal.valueOf(1000));
            app.sendMoneyToPhone("+754", BigDecimal.valueOf(100));
            app.sendMoneyToPhone("+755", BigDecimal.valueOf(1111));
            app.sendMoneyToPhone("+755", BigDecimal.valueOf(11));
            app.sendMoneyToPhone("+756", BigDecimal.valueOf(-11));

            System.out.println();
            app.printTranslationHistory();
        } catch (BankClientException bankClientException) {
            bankClientException.printStackTrace();
        }

        System.out.println();
        try {
            var app = context.getBean(TransferMoneyAppService.class);
            app.signInBankApp(new Client(1, "Masha", "+7800", BigDecimal.valueOf(1000)));
            app.sendMoneyToPhone("+799", BigDecimal.valueOf(2000));
            app.sendMoneyToPhone("+799", BigDecimal.valueOf(200));
            app.sendMoneyToPhone("+799", BigDecimal.valueOf(2222));
            app.sendMoneyToPhone("+799", BigDecimal.valueOf(22));
            app.sendMoneyToPhone("+799", BigDecimal.valueOf(-22));

            System.out.println();
            app.printTranslationHistory();
        } catch (BankClientException bankClientException) {
            bankClientException.printStackTrace();
        }
    }
}