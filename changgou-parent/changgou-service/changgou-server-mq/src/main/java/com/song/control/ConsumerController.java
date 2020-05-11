package com.song.control;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-26 17:51
 * @Description:
 */

@Component
@Slf4j
public class ConsumerController {

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "changgouqueue"),
            exchange = @Exchange(value = "exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"changgou"}
    ))
    public void consumer(Channel channel, @Payload Object message, @Headers Map map) throws IOException {
        Long id = (Long) map.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println(message);
            channel.basicAck(id,true);
        }catch (Exception e){
            log.info("消费出错",e);
            channel.basicAck(id,false);
        }

    }
}
