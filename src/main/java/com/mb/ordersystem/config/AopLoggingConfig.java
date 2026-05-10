package com.mb.ordersystem.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class AopLoggingConfig {

    @Pointcut("execution(* com.mb.ordersystem.controller.*.*(..))")
    private void controllerLayer() {}

    @Pointcut("execution(* com.mb.ordersystem.service.*.*(..))")
    private void serviceLayer() {}

    @Around("controllerLayer()")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = currentRequest();
        String method = joinPoint.getSignature().toShortString();

        if (request != null) {
            log.info("[REQUEST]  {} {} - handler={} args={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    method,
                    Arrays.toString(joinPoint.getArgs()));
        }

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsed = System.currentTimeMillis() - start;

        log.info("[RESPONSE] {} {} - {}ms - body={}",
                request != null ? request.getMethod() : "?",
                request != null ? request.getRequestURI() : "?",
                elapsed,
                result);

        return result;
    }

    @Around("serviceLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        log.debug("[SERVICE]  -> {} args={}", method, Arrays.toString(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.debug("[SERVICE]  <- {} completed in {}ms", method, elapsed);
            return result;
        } catch (Throwable ex) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("[SERVICE]  <- {} threw {} after {}ms", method, ex.getClass().getSimpleName(), elapsed);
            throw ex;
        }
    }

    private HttpServletRequest currentRequest() {
        var attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes sra) {
            return sra.getRequest();
        }
        return null;
    }
}
