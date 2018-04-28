package com.dingcheng.fastdfs.util;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.dingcheng.fastdfs.common.ClientGlobal;
import com.dingcheng.fastdfs.common.NameValuePair;
import com.dingcheng.fastdfs.common.StorageClient;
import com.dingcheng.fastdfs.common.StorageServer;
import com.dingcheng.fastdfs.common.TrackerClient;
import com.dingcheng.fastdfs.common.TrackerServer;

public class FastDFSClient {
	private static final String CONF_FILENAME = Thread.currentThread().getContextClassLoader().getResource("").getPath()
			+ "/config/fdfs_client.properties";

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(FastDFSClient.class);

	private static NameValuePair[] meta_list = new NameValuePair[1];
	private static FastDFSClient fastDFSClient = null;
	private static TrackerClient tracker = null;
	private static TrackerServer trackerServer = null;
	private static StorageClient client = null;

	private FastDFSClient() {
	}

	public static FastDFSClient newInstance() {
		if (null == fastDFSClient) {
			fastDFSClient = new FastDFSClient();

			try {
				ClientGlobal.init(CONF_FILENAME);
				meta_list[0] = new NameValuePair("author", "Shanghx");

				tracker = new TrackerClient();
				trackerServer = tracker.getConnection();
				StorageServer storageServer = null;
				client = new StorageClient(trackerServer, storageServer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fastDFSClient;
	}

	public String uploadFile(byte[] file_content, String fileExt) {
		String remote_filename = null;
		try {
			// 将内容写入文件并上传/下载
			long startTime = System.currentTimeMillis();
			String[] results = client.upload_file(file_content, fileExt, meta_list);
			//results = client.upload_file(local_filename, fileExt, meta_list);//local_filename本地文件路径
			
			System.out.println("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");

			if (results == null) {
				System.err.println("upload file fail, error code: " + client.getErrorCode());
				return null;
			} else {
				String group_name = results[0];
				remote_filename = results[1];
				System.err.println("group_name: " + group_name + ", remote_filename: " + remote_filename);
				System.err.println(client.get_file_info(group_name, remote_filename));

				/*ServerInfo[] servers = tracker.getFetchStorages(trackerServer, group_name, remote_filename);
				if (servers == null) {
					System.err.println("get storage servers fail, error code: " + tracker.getErrorCode());
				} else {
					System.err.println("storage servers count: " + servers.length);
					for (int k = 0; k < servers.length; k++) {
						System.err.println((k + 1) + ". " + servers[k].getIpAddr() + ":" + servers[k].getPort());
					}
					System.err.println("");
				}

				startTime = System.currentTimeMillis();
				int errno = client.set_metadata(group_name, remote_filename, meta_list,
						ProtoCommon.STORAGE_SET_METADATA_FLAG_MERGE);

				System.out.println("set_metadata time used: " + (System.currentTimeMillis() - startTime) + " ms");
				if (errno == 0) {
					System.err.println("set_metadata success");
				} else {
					System.err.println("set_metadata fail, error no: " + errno);
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return remote_filename;
	}

	public String downloadFile(String group_name, String remote_filename) {
		long startTime = System.currentTimeMillis();
		byte[] file_buff = null;
		try {
			//file_buff = client.download_file1(remote_filename);//不用指定group_name
			file_buff = client.download_file(group_name, remote_filename);
			// 下载到指定目录
			//int errno = client.download_file(group_name, remote_filename, 0, 0, new DownloadFileWriter("F:\\" + remote_filename.replaceAll("/", "-")));
			//不用指定长度
			//int errno = client.download_file1(remote_filename, new DownloadFileWriter("c:\\" + file_id.replaceAll("/", "-")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("download_file time used: " + (System.currentTimeMillis() - startTime) + " ms");

		/*if (file_buff != null) {
			System.out.println("file length:" + file_buff.length);
			System.out.println((new String(file_buff)));
		}*/
		try {
			return new String(file_buff, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
			String remote_filename = newInstance().uploadFile("hello world, 你好".getBytes(ClientGlobal.g_charset), "txt");
			System.out.println(newInstance().downloadFile("group1", remote_filename));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
