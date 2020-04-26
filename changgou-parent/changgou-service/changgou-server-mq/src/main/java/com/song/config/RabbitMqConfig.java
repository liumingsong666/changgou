package com.song.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-26 17:02
 * @Description:
 */

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue queue(){
        return new Queue("changgouQueue");
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("changgouEx");
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(topicExchange()).with("changgou");
    }
}
