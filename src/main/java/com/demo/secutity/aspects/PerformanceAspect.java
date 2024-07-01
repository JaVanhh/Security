package com.demo.secutity.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class PerformanceAspect {
    private Logger logger = Logger.getLogger(getClass().getName());

    private String getMethordName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    @Pointcut("within(com.demo.secutity.controller.*)")
    public void controllerMethods() {
    }

    @Before("controllerMethods()")
    public void beforeMethodExecution(JoinPoint joinPoint) {

        logger.info("Starting execution of :" + this.getMethordName(joinPoint));
    }

    @After("controllerMethods()")
    public void afterMethodExecution(JoinPoint joinPoint) {

        logger.info("finished execution of :" + this.getMethordName(joinPoint));
    }
    @Around("controllerMethods()")
    public Object measureControllerMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.nanoTime();

        Object returnValue = proceedingJoinPoint.proceed();

        long end = System.nanoTime();
        long elapsedTime = end - start;

        String methodName = proceedingJoinPoint.getSignature().getName();
        logger.info("Execution of " + methodName + " took " + elapsedTime + "ms");

        return returnValue;
    }
}
