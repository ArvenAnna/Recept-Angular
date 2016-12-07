package com.anna.recept.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This class doesn't have useful functions yet
 */
@Aspect
@Component
public class LoggingAspect {

    private Logger logger = Logger.getLogger(LoggingAspect.class);

    @Before("within(com.anna.recept.service.impl.*)")
    public void before(JoinPoint jp) {
        logger.info("Before method" + jp.getSignature().getName() + " with args......" + getArgs(jp.getArgs()));
    }

    public String getArgs(Object[] args) {
        String argsStr = "";
        for (Object arg : args) {
            argsStr = argsStr + arg.toString() + "...";
        }
        return argsStr;
    }

}
