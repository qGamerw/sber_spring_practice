package ru.sber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sber.aspects.LoggingAspect;
import ru.sber.config.ProjectConfig;
import ru.sber.exception.EmptyCollectionArgumentException;
import ru.sber.exception.EmptyStringArgumentException;
import ru.sber.exception.NullArgumentException;
import ru.sber.model.Poem;
import ru.sber.services.InteractionPoemInterfaceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Тестирование класса InteractionPoemService
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProjectConfig.class})
public class InteractionPoemTest {
    private Logger serviceLogger;
    private Logger aspectLogger;
    @Autowired
    private LoggingAspect loggingAspect;
    @Autowired
    private InteractionPoemInterfaceService commentService;

    @BeforeEach
    public void before() {
        this.aspectLogger = mock(Logger.class);
        loggingAspect.setLogger(aspectLogger);
        this.serviceLogger = mock(Logger.class);
        commentService.setLogger(serviceLogger);
    }

    @Test
    @DisplayName("Тест testPrintInfoOfPoemMethod метода на вывода в логи.")
    public void testPrintInfoOfPoemMethod() {
        Poem poem = new Poem("Natasha", "Demo text");
        try {
            commentService.printInfoOfPoem(poem);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        verify(aspectLogger).info("Aspect: Method printInfoOfPoem with parameters [Poem{createdDate=" + LocalDate.now() + ", author='Natasha', text='Demo text'}] will execute.");
        verify(serviceLogger).info("Method: Author poem: Natasha, text poem: Demo text, date: " + LocalDate.now());
    }

    @Test
    @DisplayName("Тест testEditTextNullArgumentMethod метода на null в аргументе.")
    public void testEditTextNullArgumentMethod() {
        Assertions.assertThrows(NullArgumentException.class, () -> {
            commentService.editPoemText(new Poem("Natasha", "Demo text"), null);
        });
    }

    @Test
    @DisplayName("Тест testEditTextEmptyStringMethod метода на пустоту строку.")
    public void testEditTextEmptyStringMethod() {
        Poem poem = new Poem("Natasha", "Demo text");
        Assertions.assertThrows(EmptyStringArgumentException.class, () -> {
            commentService.editPoemText(poem, "");
        });
    }

    @Test
    @DisplayName("Тест testEditTextCollectionMethod метода на пустую коллекцию.")
    public void testEditTextEmptyCollectionMethod() {
        Assertions.assertThrows(EmptyCollectionArgumentException.class, () -> {
            commentService.printPublicAuthors(new ArrayList<>());
        });
    }


    @Test
    @DisplayName("Тест testEditTextNullArgumentMethod метода на null в аргументе.")
    public void testEditTextNotNullArgumentMethod() {
        Assertions.assertDoesNotThrow(() -> {
            commentService.editPoemText(new Poem("Natasha", "Demo text"), "Demo text");
        });
    }

    @Test
    @DisplayName("Тест testEditTextEmptyStringMethod метода на строку.")
    public void testEditTextStringMethod() {
        Poem poem = new Poem("Natasha", "Demo text");
        Assertions.assertDoesNotThrow(() -> {
            commentService.editPoemText(poem, "Demo text");
        });
    }

    @Test
    @DisplayName("Тест testEditTextCollectionMethod метода на коллекцию.")
    public void testEditTextCollectionMethod() {
        Assertions.assertDoesNotThrow(() -> {
            commentService.printPublicAuthors(List.of("Oleg", "Masha", "Natasha"));
        });
    }
}
