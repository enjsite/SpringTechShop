package ru.geekbrains.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AppAspects {

    private static Logger logger = LoggerFactory.getLogger(AppAspects.class);

    @Before("execution(* ru.geekbrains.controllers.*.*(..))")
    public void before(JoinPoint joinPoint) {
        logger.info("Call of {}", joinPoint);
    }

    @Around("@annotation(ru.geekbrains.aspect.TrackTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        logger.info("Time taken by {} is {}", joinPoint, System.currentTimeMillis() - start);

        return result;
    }
}