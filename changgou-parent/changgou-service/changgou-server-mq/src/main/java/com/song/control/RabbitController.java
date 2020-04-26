package com.song.control;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.UUID;


/**
 * @Author: mingsong.liu
 * @Date: 2020-04-26 17:10
 * @Description:
 */

@Component
@Slf4j
public class RabbitController implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if(b){
            log.info("发送到交换机成功,id:{},s:{}",correlationData.getId(),s);
        }else {
            log.error("发送到交换机失败,id:{},s:{}",correlationData.getId(),s);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.error("消息为：{},i:{},s:{},s1:{},s2:{}",message.toString(),i,s,s1,s2);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void simpleMessageConverter(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
    }

    public void send(Object message,String routKey){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setMandatory(true);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                //rabbitTemplate.convertAndSend(routKey, message,correlationData);
        rabbitTemplate.convertAndSend("exchange",routKey,message,correlationData);
    }
}
