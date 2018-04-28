package com.dingcheng.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	private static CookieUtil cookie = null;
    private CookieUtil(){}  
    public static CookieUtil newInstance(){  
        if(null == cookie){  
        	cookie = new CookieUtil();  
        }  
        return cookie;  
    }  
	
	public void saveCookie(String cookieValue, String cookieDomainName, int expire, HttpServletResponse response) {
        // cookie的有效期
        //long cookieMaxAge = 60 * 60 * 5 * 1000; // 设置cookie有效期是5小时，根据需要自定义
        long validTime = System.currentTimeMillis() + expire;
        // 将要被保存的完整的Cookie值
        //String cookieValue = user.getId() + ":" + user.getToken() + ":" + validTime;
        cookieValue += ":" + validTime;
        // 再一次对Cookie的值进行BASE64编码
        String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
        // 开始保存Cookie
        //String cookieDomainName = "cn.seecom";
        Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);
        // 存两年(这个值应该大于或等于validTime)
        //cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        cookie.setMaxAge(expire);
        // cookie有效路径是网站根目录
        cookie.setPath("/");
        // 向客户端写入
        response.addCookie(cookie);
    }

    public String getMD5(String value) {
        String result = null;
        try {
            byte[] valueByte = value.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(valueByte);
            result = toHex(md.digest());
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
        return result;
    }

    // 将传递进来的字节数组转换成十六进制的字符串形式并返回
    private String toHex(byte[] buffer) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }
        return sb.toString();
    }
    
    // 用户注销时,清除Cookie,在需要时可随时调用------------------------------------------------------------
    public void clearCookie(HttpServletResponse response, String cookieDomainName) {
        //String cookieDomainName = "cn.seecom";
        Cookie cookie = new Cookie(cookieDomainName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    public String readCookie(HttpServletRequest request, HttpServletResponse response, String cookieDomainName)
            throws IOException, ServletException, UnsupportedEncodingException {
        // 根据cookieName取cookieValue
        Cookie cookies[] = request.getCookies();
        String cookieValue = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookieDomainName.equals(cookies[i].getName())) {
                    cookieValue = cookies[i].getValue();
                    break;
                }
            }
        }
        // 如果cookieValue为空,返回,
        if (cookieValue == null) {
            return null;
        }
        // 如果cookieValue不为空,才执行下面的代码
        // 先得到的CookieValue进行Base64解码
        String cookieValueAfterDecode = new String(Base64.decode(cookieValue), "utf-8");
        // 对解码后的值进行分拆,得到一个数组,如果数组长度不为2,就是非法登陆
        String cookieValues[] = cookieValueAfterDecode.split(":");
        if (cookieValues.length != 2) {
            return null;
        }
        // 判断是否在有效期内,过期就删除Cookie
        long validTimeInCookie = new Long(cookieValues[1]);
        if (validTimeInCookie < System.currentTimeMillis()) {
            // 删除Cookie
            clearCookie(response, cookieDomainName);
        }
        return cookieValues[0];
    }
}
