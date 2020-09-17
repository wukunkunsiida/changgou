package com.changgou.pay.mq;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @auther wkk
 * @date 2020/8/29 10:50
 */
@Configuration
public class Mqconfig {


    @Bean
    public Queue orderQueue() {
        return new Queue("queue.order");
    }

    @Bean
    public Exchange orderExchange() {
        return new DirectExchange("exchange.order", true, true);
    }

    @Bean
    public Binding orderQueueExchange(Queue orderQueue, Exchange exchange) {
        return BindingBuilder.bind(orderQueue).to(exchange).with("queue.order").noargs();
    }


}