package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    //부모 클래스에서 기본 생성자 외에 다른 생성자가 있는 경우
    //자식 클래스에서 부모 클래스의 생성자를 호출해 줘야함.
    public OrderServiceConcreteProxy( OrderServiceV2 target, LogTrace logTrace) {
        //기본 생성자를 호출하거나, 다른 생성자를 호출해야 하는 상황.
        //부모클래스의 기본 생성자는 없고, 해당 생성자를 이용하지도 않음. proxy역할만 함.
        //super에 null을 넘겨주자.

        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }


    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("[Proxy] OrderService.orderItem()");
            //target 호출
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
