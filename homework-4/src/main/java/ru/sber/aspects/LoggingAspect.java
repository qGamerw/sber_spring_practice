package ru.sber.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.util.CollectionUtils;
import ru.sber.exception.EmptyCollectionArgumentException;
import ru.sber.exception.EmptyStringArgumentException;
import ru.sber.exception.NullArgumentException;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Класс для реализации функции аннотации NotEmpty
 */
@Aspect
public class LoggingAspect {
    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Before("@annotation(NotEmpty)")
    public void checkEmptyArguments(JoinPoint joinPoint) throws NullArgumentException,
            EmptyCollectionArgumentException {
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        logger.info("Aspect: Method " + methodName +
                " with parameters " + Arrays.asList(arguments) + " will execute.");

        for (Object obj : arguments) {
            if (obj == null) {
                throw new NullArgumentException("Null argument in " + methodName);
            } else if (obj instanceof String && obj.toString().isEmpty()) {
                throw new EmptyStringArgumentException("Empty string in argument in " + methodName);
            } else if (obj instanceof Collection<?> && CollectionUtils.isEmpty((Collection<?>) obj)) {
                throw new EmptyCollectionArgumentException("Collection's argument is empty in argument in " + methodName);
            }
        }
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
