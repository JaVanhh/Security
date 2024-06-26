package com.demo.secutity.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.logging.Logger;

@Aspect
@Component
public class UserActivityLogger {
    private Logger logger = Logger.getLogger(getClass().getName());
    //name poincut
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void ControllerMethods() {

    }

    @Around("ControllerMethods() && execution(* com.demo.secutity.controller.AuthController.*(..))")

    public Object LogUserActivity(ProceedingJoinPoint joinPoint) throws Throwable {
        //Ghi log truoc khi thuc hien method
        String methodName = joinPoint.getSignature().getName();
        String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();
        logger.info("User activity started: " + methodName +",IP address :" + remoteAddress);
        // Thuc hien method goc
        Object result = joinPoint.proceed();
        // Ghi log sau khi thuc hien method
        logger.info("User activity finished :" + methodName);
        return result;

    }
}
