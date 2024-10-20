package com.course.project.varabei.linkshortener.service.beanpostprocessor;

import com.course.project.varabei.linkshortener.service.annotation.ExecutionTimeLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(prefix = "link-shortener.logging", value = "enable-log-execution-time", havingValue = "true")
public class LogExecutionTimeBeanPostProcessor implements BeanPostProcessor {

    private final static Logger log = LoggerFactory.getLogger(LogExecutionTimeBeanPostProcessor.class);
    private final Map<String, BeanMethodsData> beanMethodsDataMapByBeanName = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();

        for (Method method : methods) {
            boolean isAnnotated = method.isAnnotationPresent(ExecutionTimeLog.class);

            if (isAnnotated) {
                beanMethodsDataMapByBeanName.putIfAbsent(beanName, new BeanMethodsData(bean.getClass(), new ArrayList<>()));
                beanMethodsDataMapByBeanName.get(beanName).annotatedMethods().add(method);
            }
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        BeanMethodsData beanMethodsData = beanMethodsDataMapByBeanName.get(beanName);

        if (beanMethodsData == null) {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }

        Class<?> beanClass = beanMethodsData.clazz();
        List<Method> annotatedMethods = beanMethodsData.annotatedMethods();

        return Proxy.newProxyInstance(beanClass.getClassLoader(), bean.getClass().getInterfaces(), (proxy, method, args) -> {
            boolean isAnnotated = annotatedMethods.stream().anyMatch(pojoMethod -> methodEquals(pojoMethod, method));

            if (isAnnotated) {
                long startTime = System.currentTimeMillis();

                try {
                    return method.invoke(bean, args);
                } catch (Exception e) {
                    throw e.getCause();
                } finally {
                    log.info("Method execution time {}: {} ms", method.getName(), System.currentTimeMillis() - startTime);
                }
            }

            try {
                return method.invoke(bean, args);
            } catch (Exception e) {
                throw e.getCause();
            }
        });
    }

    public boolean methodEquals(Method method1, Method method2) {
        if (method1.getName().equals(method2.getName())) {
            return equalParamTypes(method1.getParameterTypes(), method2.getParameterTypes());
        }
        return false;
    }

    private boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i])
                    return false;
            }
            return true;
        }
        return false;
    }

    private record BeanMethodsData(Class<?> clazz, List<Method> annotatedMethods) {

    }
}
