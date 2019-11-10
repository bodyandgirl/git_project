package com.web;

import com.config.RabbitmqConfig;
import com.entity.common.ResponseBody;
import com.entity.test.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbit")
@Slf4j
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitmqConfig rabbitmqConfig;

    @GetMapping("/sendMsg/{msg}")
    public ResponseBody sendSimpleMessage(@PathVariable String msg){
       try{
           Message message = MessageBuilder.withBody(msg.getBytes("UTF-8")).build();
           rabbitTemplate.setExchange(rabbitmqConfig.getDirectExchange());
           rabbitTemplate.setRoutingKey(rabbitmqConfig.getDirectReouteKey());
           rabbitTemplate.send(message);

           log.info("发送信息中");
       }catch(Exception e){
           log.error("发送信息错误：{}",e);
       }
      return new ResponseBody().toBuilder()
              .status(HttpStatus.OK)
              .build();
    }

    @PostMapping(value = "/sendObj",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseBody sendObjectMessage(@Validated @RequestBody User user){
        try{
            rabbitTemplate.setExchange(rabbitmqConfig.getDirectExchange());
            rabbitTemplate.setRoutingKey(rabbitmqConfig.getDirectReouteKey());
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(user)).build();
            rabbitTemplate.send(message);
            log.info("发送对象成功");
        }catch(Exception e){
            log.error("发送信息对象错误：{}",e);
        }
        return new ResponseBody().toBuilder()
                .status(HttpStatus.OK)
                .build();
    }
}
