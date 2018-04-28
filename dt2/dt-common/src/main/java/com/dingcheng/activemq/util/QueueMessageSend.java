package com.dingcheng.activemq.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class QueueMessageSend {

	private static JmsTemplate jmsTemplate;
	public static JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		QueueMessageSend.jmsTemplate = jmsTemplate;
	}

	public static void send(final String msgTxt) {
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage msg = session.createTextMessage();
				msg.setText(msgTxt);
				System.out.println("发送数据++++++++++++"+msgTxt+"+++++++++++++完成!");
				return msg;
			}
		});
	}
}
