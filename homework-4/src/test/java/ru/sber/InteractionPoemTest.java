package ru.sber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sber.aspects.LoggingAspect;
import ru.sber.config.ProjectConfig;
import ru.sber.model.Poem;
import ru.sber.services.InteractionPoemService;

import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProjectConfig.class})
public class InteractionPoemTest {
    private Logger serviceLogger;
    private Logger aspectLogger;

    @Autowired
    private LoggingAspect loggingAspect;

    @Autowired
    private InteractionPoemService commentService;

    @BeforeEach
    public void before() {
        this.aspectLogger = mock(Logger.class);
        loggingAspect.setLogger(aspectLogger);

        this.serviceLogger = mock(Logger.class);
        commentService.setLogger(serviceLogger);
    }

    @Test
    @DisplayName("Test that the aspect intercepts and alters the execution" +
            " of the deleteComment() method.")
    public void testAspectInterceptsDeleteCommentMethod() {
        Poem poem = new Poem("Natasha", "Demo text");
        try {
            commentService.editText(poem, null);
        } catch (Throwable throwable){
            throwable.printStackTrace();
        }
        verify(aspectLogger).info("Aspect: Method editText with parameters [Poem{createdDate=2023-06-22, author='Natasha', text='Demo text'}, null] will execute");
        verify(aspectLogger).info("Aspect: Method editText with parameters [Poem{createdDate=2023-06-22, author='Natasha', text='Demo text'}, null] will execute");
    }


}
