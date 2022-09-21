package com.cybernite.flying.aop;

import com.cybernite.flying.common.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Log4j2
@AllArgsConstructor
public class PushNotificationAspect {

    SimpMessagingTemplate smt;


    @Around(value = "execution(public * com.cybernite.flying.controllers..*(..))")
    Object proceed(ProceedingJoinPoint jp) throws Throwable {
        String className = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        log.info("new request {} of class {}", methodName, className);
        Arrays.stream(jp.getArgs()).forEach(obj->{
            log.debug("argument clazz {}", obj);
        });
        smt.convertAndSend(Constants.WebSocket.TOPIC, methodName);
        return jp.proceed();
    }
}
