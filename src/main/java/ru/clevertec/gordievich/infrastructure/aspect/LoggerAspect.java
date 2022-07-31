package ru.clevertec.gordievich.infrastructure.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAspect {

    private static Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Around("ru.clevertec.gordievich.infrastructure.aspect.JoinPointConfigure.loggerPointCut()")
    public Object logger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Gson parser = new Gson();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        logger.info("{} args={}", methodSignature.getMethod().getName(), parser.toJson(proceedingJoinPoint.getArgs()));
        String result = (String) proceedingJoinPoint.proceed();
        logger.info("{} result=\n{}", methodSignature.getMethod().getName(), /*parser.toJson(result)*/result);
        return result;
    }

}
