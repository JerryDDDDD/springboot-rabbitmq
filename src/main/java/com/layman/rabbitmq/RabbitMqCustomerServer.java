package com.layman.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @ClassName RabbitMqCustomerServer
 * @Description TODO
 * @Author 叶泽文
 * @Data 2019/10/11 11:11
 * @Version 3.0
 **/
public class RabbitMqCustomerServer {

    private static final Logger logger = Logger.getLogger(RabbitMqCustomerServer.class.getName());

    public RabbitMqCustomerServer() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.103");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("fanoutLogs", BuiltinExchangeType.FANOUT);


            // 获取一个临时队列
            String queueName = channel.queueDeclare().getQueue();
            logger.info(queueName);
            // 队列与交换机绑定
            channel.queueBind(queueName, "fanoutLogs", "");

            logger.info("***********Waiting for messages**************");


            //这里重写了DefaultConsumer的handleDelivery方法，因为发送的时候对消息进行了getByte()，在这里要重新组装成String
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    String message = new String(body, "UTF-8");
                    logger.info("received:" + message);
                }
            };

            //声明队列中被消费掉的消息（参数为：队列名称；消息是否自动确认;consumer主体）
            channel.basicConsume(queueName, true, consumer);
            //这里不能关闭连接，调用了消费方法后，消费者会一直连接着rabbitMQ等待消费

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class SingletonInstance {
        private static final RabbitMqCustomerServer instance = new RabbitMqCustomerServer();
    }

    public static RabbitMqCustomerServer getInstance() {
        return SingletonInstance.instance;
    }
}
