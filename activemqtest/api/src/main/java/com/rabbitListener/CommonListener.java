package com.rabbitListener;

import com.entity.test.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Slf4j
public class CommonListener {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${basic.rabbit.direct.queue}",containerFactory = "singleListenerContainer")
    public void consumeMessage(@Payload byte[] message){
        try{
            /*String result = new String(message,"utf-8");
            log.info("接收到消息：{}",result);*/

             User u = objectMapper.readValue(message, User.class);
            log.info("接收到信息：{}",u);

        }catch(Exception e){
            log.error("接收信息失败：{}" ,e);
        }
    }
}
