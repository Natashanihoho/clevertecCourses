package ru.clevertec.gordievich.infrastructure.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class JoinPointConfigure {

    @Pointcut("@annotation(ru.clevertec.gordievich.infrastructure.aspect.annotation.CustomLog)")
    public void loggerPointCut(){

    }
}
