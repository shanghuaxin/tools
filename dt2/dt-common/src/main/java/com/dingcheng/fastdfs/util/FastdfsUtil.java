package com.dingcheng.fastdfs.util;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dingcheng.common.util.JSONUtil;
import com.dingcheng.common.util.SysConst;

public class FastdfsUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void uplaodFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		long maxSize = 1000000;
		PrintWriter out = response.getWriter();

		String dirName = request.getParameter("dir");

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException ex) {
		}
		Iterator<FileItem> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = itr.next();

			long fileSize = item.getSize();
			if (!item.isFormField()) {
				String fileName = item.getName();
				// 检查文件大小
				if (fileSize > maxSize) {
					out.println("上传文件大小超过限制，最大：" + maxSize + ",当前：" + fileSize);
					return;
				}
				// 检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
					out.println("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
					return;
				}

				String fileUrl = "";
				try {
					fileUrl = FastDFSClient.newInstance().uploadFile(item.get(), fileExt);
				} catch (Exception e) {
					try {
						fileUrl = FastDFSClient.newInstance().uploadFile(item.get(), fileExt);
					} catch (Exception e2) {
						out.println("上传文件失败。");
						return;
					}
				}

				// 输出
				Map obj = new HashMap();
				obj.put("error", 0);
				obj.put("url", SysConst.UPLOAD_FILE_BASE_URL + fileUrl);
				out.println(JSONUtil.objectToJson(obj));
			}
		}

	}
}
