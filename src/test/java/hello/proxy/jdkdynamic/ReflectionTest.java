package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA(); // 호출하는 메서드만 다름.
        log.info("result = {}", result1);

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();// 호출하는 메서드만 다름.
        log.info("result = {}", result2);
    }

    @Test
    void reflection1() throws Exception{
        //클래스 정보획득
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello"); //내부에 있을 경우 $

        Hello target = new Hello();

        //문자로 callA 메서드 정보 획득
        Method methodCallA = classHello.getMethod("callA");

        //인스턴스(target)에 있는 메서드 호출.
        Object result1 = methodCallA.invoke(target);
        log.info("result1 = {}", result1);

        //문자로 callB 메서드 정보 획득
        Method methodCallB = classHello.getMethod("callB");

        //인스턴스(target)에 있는 메서드 호출
        Object result2 = methodCallB.invoke(target);
        log.info("result2 = {}", result2);

    }

    @Test
    void reflectionTest2() throws Exception{
        //클래스 정보획득
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello"); //내부에 있을 경우 $

        Hello target = new Hello();

        //문자로 callA 메서드 정보 획득
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        //문자로 callB 메서드 정보 획득
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);

    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }

    @Slf4j
    static class Hello{
        public String callA(){
            log.info("callA");
            return "A";
        }

        public String callB(){
            log.info("callB");
            return "B";
        }
    }
}

