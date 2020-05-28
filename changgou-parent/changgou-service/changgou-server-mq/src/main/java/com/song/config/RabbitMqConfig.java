package com.song.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-26 17:02
 * @Description: 可以这样先制定队列和交换机，也可以在消费端指定
 */

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue queue() {
        return new Queue("changgouQueue");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("changgouEx");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("changgou");
    }


    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public DirectExchange userOrderDelayExchange() {
        return new DirectExchange("changgou.order.delay_exchange");
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue userOrderDelayQueue() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("x-dead-letter-exchange", "changgou.order.receive_exchange");
        map.put("x-dead-letter-routing-key", "changgou.order.receive_key");
        return new Queue("changgou.order.delay_queue", true, false, false, map);
    }

    /**
     * 给死信队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding userOrderDelayBinding() {
        return BindingBuilder.bind(userOrderDelayQueue()).to(userOrderDelayExchange()).with("changgou.order.delay_key");
    }


    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("changgou.order.receive_exchange");
    }

    @Bean
    public Queue orderQueue() {
        return new Queue("changgou.order.receive_queue");
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("changgou.order.receive_key");
    }
}
