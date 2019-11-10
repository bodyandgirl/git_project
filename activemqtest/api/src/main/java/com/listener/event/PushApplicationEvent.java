package com.listener.event;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Data
@ToString
public class PushApplicationEvent extends ApplicationEvent {
    private String orderType;

    private String orderNo;

    public PushApplicationEvent(Object source) {
        super(source);
    }
    public PushApplicationEvent(Object source,String orderType,String orderNo) {
        super(source);
        this.orderType = orderType;
        this.orderNo = orderNo;
    }
}
