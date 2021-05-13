package com.lk.modules.rabiitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Producer05_header {
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_HEADERS_INFORM = "exchange_header_inform";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_HEADERS_INFORM, BuiltinExchangeType.HEADERS);
        Map<String, Object> headersEmail = new Hashtable<>();
        headersEmail.put("inform_type", "email");
        Map<String, Object> headersSms = new Hashtable<>();
        headersSms.put("inform_type", "sms");
        channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_HEADERS_INFORM, "", headersEmail);
        channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_HEADERS_INFORM, "", headersSms);

        for (int i = 0; i < 5; i++) {
            Map<String, Object> headers = new Hashtable<String, Object>();
            //匹配email通知消费者绑定的header
            headers.put("inform_type", "email");
            //headers.put("inform_type", "sms");//匹配sms通知消费者绑定的header
            AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
            properties.headers(headers);
            String message = "send sms inform message to user" + i;
            channel.basicPublish(EXCHANGE_HEADERS_INFORM, "", properties.build(), message.getBytes(StandardCharsets.UTF_8));
        }
        System.out.println(111);
    }
}
