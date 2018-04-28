package com.dingcheng.activemq.util;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 只提供一个接口
 * @author Shanghuaxin
 *
 */
public class QueueMessageListener implements MessageListener {

	public void onMessage(Message message) {
		System.out.println("监听==================监听");
		try {
			System.out.println(message);
			TextMessage tm = (TextMessage) (message);
			System.out.println(tm.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
