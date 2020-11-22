package com.lk.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * @author laok
 */
@Aspect
@Component
public class ParamsAspect {
    /**
     * 在方法执行之前、后执行该方法
     * 设置需要执行该方法的类路径和注解
     */
    @Around("execution(* com.lk.controller.*.*(..)) && (@annotation(com.lk.interceptor.ParamsAopAnnotation))")
    public Object interceptor(ProceedingJoinPoint point) throws Throwable {
        // 获取请求参数
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) arg;
                ChangeRequestWrapper requestWrapper = new ChangeRequestWrapper(request);
                requestWrapper.setParameter("a", "0");
                args[i] = requestWrapper;
            }
        }
        return point.proceed(args);
    }
}
