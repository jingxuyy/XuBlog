package com.xujing.aspect;

import com.alibaba.fastjson.JSON;
import com.xujing.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 86136
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.xujing.annotation.SystemLog)")
    public void pt(){}

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            log.info("========End========"+System.lineSeparator());
        }

        return ret;
    }

    private void handleAfter(Object ret) {
        log.info("Response        : {}", JSON.toJSONString(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        // 获取URL
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLogAnnotation(joinPoint);

        log.info("========Start========");
        // 请求的URL
        log.info("URL        : {}", request.getRequestURL());
        // 接口描述信息
        log.info("BusinessName  : {}", systemLog.businessName());
        // 请求的方法的方式
        log.info("HTTP Method   : {}", request.getMethod());
        // Controller 的全路径
        log.info("Class Method  : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getName());
        // 请求的IP
        log.info("IP            : {}", request.getRemoteHost());
        // 请求的参数
        log.info("Request Args  : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLogAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = signature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }

}
