package com.example.demo.aop;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@RequiredArgsConstructor
public class ServiceMethodTracingAspect {

    private final ObservationRegistry observationRegistry;

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceMethods(){}

    @Pointcut("within(com.example.demo.*)")
    public void withinMyPackage(){}

    @Around("serviceMethods() || withinMyPackage()")
    public Object observeServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getStaticPart().getSignature();
        Observation observation = Observation.createNotStarted(signature.getDeclaringType().getSimpleName() + "#" + signature.getName(), observationRegistry);
        return observation.observeChecked(() -> joinPoint.proceed());
    }

}
