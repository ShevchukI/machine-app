package com.shevchuk.machineapp.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectLogger {
    private static Logger LOG = LogManager.getLogger(AspectLogger.class);

    @Pointcut("within(com.shevchuk.machineapp..*)")
    public void allPointcut() {

    }

    @Around("execution(* com.shevchuk.machineapp.handlers.ResponseExceptionHandler.*(..))")
    public Object logResponseExceptionHandler(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        Object object = proceedingJoinPoint.proceed();
        String className = proceedingJoinPoint.getTarget().getClass().getName();

        Arrays.stream(args)
                .filter(arg-> arg instanceof RuntimeException)
                .forEach(arg ->{
                    LOG = LogManager.getLogger(className);
                    LOG.error(((RuntimeException) arg).getMessage());
                });

        return object;
    }

    @Around("bean(*Controller)")
    public Object logControllerMethods(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        Object object = proceedingJoinPoint.proceed();

        if (LOG.isInfoEnabled()) {
            LOG = LogManager.getLogger(className);
            LOG.info(methodName + "() " + "Response : "
                    + new ObjectMapper().writeValueAsString(object));
        }

        return object;
    }

    @Around("bean(*Service)")
    public Object logServiceMethods(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        Object object = proceedingJoinPoint.proceed();

        if (LOG.isTraceEnabled()) {
            LOG = LogManager.getLogger(className);
            LOG.trace(methodName + "() " + "Return : "
                    + new ObjectMapper().writeValueAsString(object));
        }

        return object;
    }

    @AfterThrowing(value = "allPointcut()", throwing = "e")
    public void logAllThrowing(final JoinPoint joinPoint, final Throwable e) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();
        StringBuilder arguments = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            arguments.append(i + 1).append(": ").append(args[i]).append(";\n\t");
        }

        LOG = LogManager.getLogger(className);
        LOG.error(String.format("%s() argument:\n\t%s", methodName, arguments), e);
    }
}