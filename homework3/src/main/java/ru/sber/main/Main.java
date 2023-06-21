package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.exception.BankClientException;
import ru.sber.model.Client;
import ru.sber.services.ApplicationService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var app = context.getBean(ApplicationService.class);

        try {
            app.checkClient(new Client(1, "Oleg", "+7809", BigDecimal.valueOf(1000)));
            app.sendMoney("+753", BigDecimal.valueOf(1000));
            app.sendMoney("+754", BigDecimal.valueOf(100));
            app.sendMoney("+755", BigDecimal.valueOf(1111));
            app.sendMoney("+755", BigDecimal.valueOf(11));
            app.sendMoney("+756", BigDecimal.valueOf(-11));

            System.out.println();
            app.printHistory();
        } catch (BankClientException bankClientException) {
            bankClientException.printStackTrace();
        }
    }
}