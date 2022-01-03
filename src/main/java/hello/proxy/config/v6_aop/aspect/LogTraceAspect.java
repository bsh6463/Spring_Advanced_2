package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))") //point cut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        //advice 로직

        TraceStatus status = null;
        try{
            String message = joinPoint.getSignature().toShortString(); //joint point의 signature 반환.
            status = logTrace.begin(message);

            //로직 호출
            Object result = joinPoint.proceed();
            logTrace.end(status);

            return result;
        }catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
