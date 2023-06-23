package ru.sber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sber.aspects.NotEmptyAspect;
import ru.sber.config.ProjectConfig;
import ru.sber.exception.EmptyCollectionArgumentException;
import ru.sber.exception.EmptyStringArgumentException;
import ru.sber.exception.NullArgumentException;
import ru.sber.model.Poem;
import ru.sber.services.InteractionPoemInterfaceService;
import ru.sber.services.InteractionPoemService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Тестирование функций класса {@link  InteractionPoemService}
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProjectConfig.class})
public class InteractionPoemTest {
    private Logger serviceLogger;
    private Logger aspectLogger;
    @Autowired
    private NotEmptyAspect notEmptyAspect;
    @Autowired
    private InteractionPoemInterfaceService commentService;

    @BeforeEach
    public void before() {
        this.aspectLogger = mock(Logger.class);
        notEmptyAspect.setLogger(aspectLogger);
        this.serviceLogger = mock(Logger.class);
        commentService.setLogger(serviceLogger);
    }

    @Test
    @DisplayName("Вызов функции после аннотации.")
    public void testPrintInfoOfPoemMethod() {
        try {
            commentService.printInfoOfPoem(new Poem("Natasha", "Demo text"));
        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();
        }
        verify(aspectLogger).info("Aspect: Method printInfoOfPoem with parameters [Poem{createdDate=" +
                LocalDate.now() + ", author='Natasha', text='Demo text'}] will execute.");
        verify(serviceLogger).info("Method: Author poem: Natasha, text poem: Demo text, date: " + LocalDate.now());
    }

    @Test
    @DisplayName("Объект null в аргументе.")
    public void testEditTextNullArgumentMethod() {
        Assertions.assertThrows(NullArgumentException.class, () -> {
            commentService.editPoemText(new Poem("Natasha", "Demo text"), null);
        });
    }

    @Test
    @DisplayName("Пустая строка в аргументе.")
    public void testEditTextEmptyStringMethod() {
        Assertions.assertThrows(EmptyStringArgumentException.class, () -> {
            commentService.editPoemText(new Poem("Natasha", "Demo text"), "");
        });
    }

    @Test
    @DisplayName("Пустая коллекция в аргументе.")
    public void testEditTextEmptyCollectionMethod() {
        Assertions.assertThrows(EmptyCollectionArgumentException.class, () -> {
            commentService.printPublicAuthors(new ArrayList<>());
        });
    }


    @Test
    @DisplayName("Заполненная строка в аргументе.")
    public void testEditTextNotNullArgumentMethod() {
        Assertions.assertDoesNotThrow(() -> {
            commentService.editPoemText(new Poem("Natasha", "Demo text"), "Demo text");
        });
    }

    @Test
    @DisplayName("Заполоненная коллекция в аргументе.")
    public void testEditTextCollectionMethod() {
        Assertions.assertDoesNotThrow(() -> {
            commentService.printPublicAuthors(List.of("Oleg", "Masha", "Natasha"));
        });
    }
}
