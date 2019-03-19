package com.msoft.rabbitmq;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	
	private final static String TASK_QUEUE_NAME = "task_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try(Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			Scanner scanner = new Scanner(System.in)) {
			
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
			System.out.println("Enter a String : ");
			
			while(true) {
				String message = scanner.nextLine();
				
				channel.basicPublish("", 
						TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
						message.getBytes("UTF-8"));
				
				System.out.println(" [x] Sent '" + message + "'");
			}
			
		}
		
	}
	
}
