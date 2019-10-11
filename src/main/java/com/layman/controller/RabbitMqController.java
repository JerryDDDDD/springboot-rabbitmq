package com.layman.controller;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * @ClassName RabbitMqController
 * @Description TODO
 * @Author 叶泽文
 * @Data 2019/10/11 10:50
 * @Version 3.0
 **/
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqController {

    private static final Logger logger = Logger.getLogger(RabbitMqController.class.getName());

    @GetMapping("/send")
    public void sendRabbitMq() {
        //
        ConnectionFactory factory = new ConnectionFactory();
        // 设置需要连接的RabbitMq地址
        factory.setHost("192.168.1.103");
        try {
            // 获取连接
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String message = "当前时间: 2019年10月11日 10:04:52";
            channel.exchangeDeclare("fanoutLogs", BuiltinExchangeType.FANOUT);

            channel.basicPublish("fanoutLogs","", null,message.getBytes());
            logger.info("********Message********:发送成功");
            channel.close();
            connection.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
