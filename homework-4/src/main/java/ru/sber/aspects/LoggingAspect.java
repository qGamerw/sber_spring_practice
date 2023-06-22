package ru.sber.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ru.sber.exception.CollectionArgumentException;
import ru.sber.exception.IncorrectArgumentException;
import ru.sber.exception.StringArgumentException;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Класс для обработки метода с аннотацией NotEmpty
 */
@Aspect
public class LoggingAspect {
    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("@annotation(NotEmpty)")
    public Object log(ProceedingJoinPoint joinPoint) throws IncorrectArgumentException,
            StringArgumentException,
            CollectionArgumentException,
            Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        logger.info("Aspect: Method " + methodName +
                " with parameters " + Arrays.asList(arguments) + " will execute");

        for (Object obj : arguments) {
            if (obj == null) {
                throw new IncorrectArgumentException("Null argument in " + methodName);
            } else if (obj.toString().isEmpty()) {
                throw new IncorrectArgumentException("Empty argument in " + methodName);
            } else if (obj instanceof String) {
                throw new StringArgumentException("String argument in " + methodName);
            } else if (obj instanceof Collection<?>) {
                throw new CollectionArgumentException("Collection argument in " + methodName);
            }
        }

        return joinPoint.proceed();
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
