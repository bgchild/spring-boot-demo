package com.lk.modules.rabiitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class Producer01 {
    public static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        //通过链接工厂创建新的连接和mq建立连接
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机，一个mq服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        try {
            //建立连接
            connection = connectionFactory.newConnection();
            //创建会话通道，生产者和mq服务所有通信都在channel通道中完成
            channel = connection.createChannel();
            //声明队列，如果队列在mq重没有则要创建
            channel.queueDeclare(QUEUE, true, false, false, null);
            String message = "hello world 黑马程序员";
            channel.basicPublish("", QUEUE, null, message.getBytes());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(channel).close();
            Objects.requireNonNull(connection).close();
        }
        System.out.println("Producer01+ok");
    }


}
