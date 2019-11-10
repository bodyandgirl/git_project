package com.listener;

import com.entity.OrderRecord;
import com.listener.event.PushApplicationEvent;
import com.service.IOrderRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PushEventListener implements ApplicationListener<PushApplicationEvent> {
    @Autowired
    private IOrderRecordService orderRecordService;

    @Override
    @Async
    public void onApplicationEvent(PushApplicationEvent event) {
        try {
            log.info("监听到事件对象：{}", event);
            //TODO  异步添加到数据库
            OrderRecord  orderRecord = new OrderRecord();
            BeanUtils.copyProperties(event,orderRecord);
            orderRecordService.insertEntity(orderRecord);
        }catch(Exception e ){
            log.error("监听器对象出错：{}",e);
        }
    }
}
