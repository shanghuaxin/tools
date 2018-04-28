//$Id: StringHelper.java,v 1.1 2008/02/13 02:40:32 administrator Exp $
package com.dingcheng.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringHelper {
	
	private final static String REGEX_PHONE = "^((13[0-9])|(14[57])|(15[012356789])|(17[01678])|(18[0-9]))\\d{8}$";
	private final static String REGEX_EMAIL = "\\A([^@\\s]+)@((?:[-a-z0-9]+\\.)+[a-z]{2,})\\Z";
	
	private StringHelper() { /* static methods only - hide constructor */
	}
	
	/**
	 * 获取订单号
	* @return
	* @Author: 315737546
	* @Date: 2016年7月5日
	 */
	public static String getOrderNum(){
		return new Date().getTime() + String.valueOf(Math.random() * 900 + 100).substring(0, 3);
	}
	
	/**
	 * 隐藏手机号中间5位
	* @param phone
	* @return
	* @Author: 315737546
	* @Date: 2015年8月26日
	 */
	public static String encryptPhone(String phone){
		if(phone == null){
			return null;
		}
		if(phone.length()<10){
			return "1****";
		}
	    String startPhone = phone.substring(0, 3);
	    String endPhome = phone.substring(8, phone.length());
	    return startPhone + "*****" + endPhome;
	}
	
	/**
	 * 判断是否是手机号
	* @param code
	* @return
	* @Author: 315737546
	* @Date: 2015年6月19日
	 */
	public static boolean isPhone(String code){
		if(code==null || code.equals("")) return false;
		Pattern p = Pattern.compile(REGEX_PHONE);  
		Matcher m = p.matcher(code);  
		return m.matches();
	}
	
	/**
	 *  判断是否是邮箱
	* @param code
	* @return
	* @Author: zongyannan
	* @Date: 2015年7月8日
	 */
	public static boolean isEmail(String code){
		if(code==null || code.equals("")) return false;
		Pattern p = Pattern.compile(REGEX_EMAIL);  
		Matcher m = p.matcher(code);  
		return m.matches();
	}
	
	public static String digitToString(String a) {
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String weight[] = { "", "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟" };
		String jg = new String();
		String tmp = a;
		String zs = new String();
		String xs = new String();
		int dot = tmp.indexOf(".");
		if (dot > 0) {
			zs = tmp.substring(0, dot);
			xs = tmp.substring(dot + 1);
		} else
			zs = tmp.substring(0);
		int w = 0;
		boolean flag = false;
		for (int i = 0; i < zs.length(); i++) {
			w = Integer.parseInt(zs.substring(i, i + 1));
			if (w == 0) {
				if ((zs.length() - i) == 5)
					jg = jg + "万";
				if ((zs.length() - i) == 9)
					jg = jg + "亿";
				flag = true;
			} else {
				if (flag) {
					jg = jg + "零";
					flag = false;
				}
				jg = jg + digit[w];
				jg = jg + weight[zs.length() - i];
			}
		}
		if (dot > 0) {
			jg = jg + "点";
			for (int i = 0; i < xs.length(); i++) {
				w = Integer.parseInt(xs.substring(i, i + 1));
				jg = jg + digit[w];
			}
		}
		return jg;
	}
	public static void main(String[] args) throws UnsupportedEncodingException{
		//system.out.println(isPhone("14610557277"));
//		//system.out.println(getByteLen("大家好11111你是谁啊</br>"));
//		String content="大</br>";
//		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]+");
//		Matcher matcher = pattern.matcher(content);
//		while(matcher.find()){
//			//system.out.println(matcher.group());
//		}
//		var a = b.match(/[^\x00-\x80]/g);
//        return b.length + (a ? a.length: 0)
	}
}
