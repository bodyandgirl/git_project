package com.config;

import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Slf4j
//@Component
public class RabbitmqConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Value("${basic.rabbit.listener.simple.concurrency}")
    private int simpleConcurrency;
    @Value("${basic.rabbit.listener.simple.max-concurrency}")
    private int simpleMaxConcurrency;
    @Value("${basic.rabbit.listener.simple.prefetch}")
    private int simplePrefetch;
    @Value("${basic.rabbit.direct.exchange}")
    private String directExchange;
    @Value("${basic.rabbit.direct.route-key}")
    private String directReouteKey;
    @Value("${basic.rabbit.direct.queue}")
    private String directQueue;

    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory singlelistenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        return factory;
    }

    @Bean(name="multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multilistenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(simpleConcurrency);
        factory.setMaxConcurrentConsumers(simpleMaxConcurrency);
        factory.setPrefetchCount(simplePrefetch);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(
                new RabbitTemplate.ConfirmCallback() {
                    @Override
                    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                        log.info("消息发送成功:correlationData: ({}) , ack : ({}) ,cause: ({})",correlationData,ack,cause);
                    }
                }
        );
        rabbitTemplate.setReturnCallback(
                new RabbitTemplate.ReturnCallback() {
                    @Override
                    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                        log.info("消息发送失败：exchange:({}) ,route({}) , replyCode({}),replyText:({}),message:({})",exchange,routingKey,replyCode,replyText,message);
                    }
                }
        );
        return rabbitTemplate;
    }


    @Bean
    public DirectExchange basicExchange(){
        return new DirectExchange(directExchange,true,false);
    }

    @Bean(name="basicQueue")
    public Queue basicQueue(){
        return new Queue(directQueue,true);
    }

    @Bean
    public Binding basicBinding(){
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(directReouteKey);
    }

    public int getSimpleConcurrency() {
        return simpleConcurrency;
    }

    public int getSimpleMaxConcurrency() {
        return simpleMaxConcurrency;
    }

    public int getSimplePrefetch() {
        return simplePrefetch;
    }

    public String getDirectExchange() {
        return directExchange;
    }

    public String getDirectReouteKey() {
        return directReouteKey;
    }

    public String getDirectQueue() {
        return directQueue;
    }
}
