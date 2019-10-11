package com.layman.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * @ClassName RabbitMqBoot
 * @Description TODO
 * @Author 叶泽文
 * @Data 2019/10/11 10:56
 * @Version 3.0
 **/
@Component
public class RabbitMqBoot implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger.getLogger(RabbitMqBoot.class.getName());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        RabbitMqCustomerServer.getInstance();
    }
}
