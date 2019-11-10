package com.web;

import com.entity.common.ResponseBody;
import com.listener.event.PushApplicationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/push")
@Slf4j
public class PushController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/testEvent/{orderType}/{orderNo}")
    public ResponseBody pushEvent(@PathVariable("orderType") String orderType, @PathVariable("orderNo") String orderNo){
        try {
            PushApplicationEvent pushApplicationEvent = new PushApplicationEvent(this, orderType, orderNo);
            eventPublisher.publishEvent(pushApplicationEvent);
        }catch(Exception e){
            log.error("下单发生异常：",e.fillInStackTrace());
        }
        return new ResponseBody().toBuilder()
                .status(HttpStatus.OK)
                .build();
    }
}
