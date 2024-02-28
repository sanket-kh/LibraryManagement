package com.librarymanagement.LibraryApplication.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Log4j2
@Component
public class LoggingAspect {

    @Before("execution(* com.librarymanagement.LibraryApplication.services.serviceImpls.*.*(..))")
    public void beforeServiceCalled(JoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        final String className = signature.getDeclaringTypeName().substring(63);
        final String methodName = signature.getName();
        log.info("Entered --> " + className + " :: " + methodName);
    }


}
