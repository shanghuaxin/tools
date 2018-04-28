package com.dingcheng.fastdfs.test;

import java.util.Properties;

import com.dingcheng.fastdfs.common.ClientGlobal;

/**
 * Created by James on 2017/5/19.
 */
public class ClientGlobalTests {

  public static void main(String[] args) throws Exception {
    String trackerServers = "192.168.70.105:22122";
    ClientGlobal.initByTrackers(trackerServers);
    System.out.println("ClientGlobal.configInfo() : " + ClientGlobal.configInfo());

    String propFilePath = "fastdfs-client.properties";
    ClientGlobal.initByProperties(propFilePath);
    System.out.println("ClientGlobal.configInfo() : " + ClientGlobal.configInfo());

    Properties props = new Properties();
    props.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, "192.168.70.105:22122");
    ClientGlobal.initByProperties(props);
    System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());

  }

}
