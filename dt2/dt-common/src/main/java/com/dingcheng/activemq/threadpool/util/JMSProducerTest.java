package com.dingcheng.activemq.threadpool.util;

import java.util.HashMap;  
import java.util.Map;  
  
public class JMSProducerTest {  
  
      
    public static void main(String[] args) {  
          
        locationTest();  
        System.out.println("over.");  
    }  
      
    private static void locationTest() {  
        //**  JMSProducer 可以设置成全局的静态变量，只需实例化一次即可使用,禁止循环重复实例化JMSProducer(因为其内部存在一个线程池)  
    	JMSProducer producer = new JMSProducer("tcp://localhost:61616", "admin", "admin");  
    	//支持openwire协议的默认连接为 tcp://localhost:61616，支持 stomp协议的默认连接为tcp://localhost:61613。   
    	//tcp和nio的区别  
    	//nio://localhost:61617 以及 tcp://localhost:61616均可在 activemq.xml配置文件中进行配置  
          
    	for(int j=0;j<50;j++) {
    		for(int i=0;i<300;i++) {
    			Map<String, Object> map = new HashMap<String, Object>();  
    			map.put("id", i+j);  
    			map.put("name", "name-->"+i+j);  
    			map.put("password", "password--->"+i+j);  
    			producer.send("test", map);
    		}
    		try {
				Thread.sleep(1000*3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }  
      
} 
