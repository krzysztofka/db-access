package com.kali.dbaccess.logging;

import com.google.common.collect.ImmutableMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

@Aspect
@Component
public class ExecutionTimeReportingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeReportingAspect.class);

    private Map<Severity, Consumer<String>> severityLoggerMap = ImmutableMap.<Severity, Consumer<String>>builder()
            .put(Severity.DEBUG, logger::debug)
            .put(Severity.INFO, logger::info)
            .put(Severity.TRACE, logger::trace)
            .put(Severity.WARN, logger::warn)
            .build();

    @Around("execution(@com.kali.dbaccess.logging.LogExecutionTime * *(..)) && @annotation(logExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint pjp, LogExecutionTime logExecutionTime) throws Throwable {
        Instant start = Instant.now();

        Object result = pjp.proceed();

        Duration duration = Duration.between(start, Instant.now());

        String message = message(duration, pjp.getTarget().getClass().getName(), pjp.getSignature().getName());

        severityLoggerMap.get(logExecutionTime.severity()).accept(message);

        return result;
    }

    private String message(Duration duration, String target, String method  ) {
        return new StringBuilder(target)
                .append(".")
                .append(method)
                .append(" executed in: ")
                .append(duration.toString())
                .toString();
    }

}
