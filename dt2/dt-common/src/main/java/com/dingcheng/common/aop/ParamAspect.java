package com.dingcheng.common.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * ********************************* 
 * 用于异常时候记录请求的参数
 * @author: 315737546
 * @createdAt: 2016年6月25日下午5:00:33
 **********************************
 */
public class ParamAspect {
	private Logger logger = Logger.getLogger(ParamAspect.class);
	// public void doAfter(JoinPoint jp) {
	// }
	//
	// public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
	// }
	//
	// public void doBefore(JoinPoint jp) {
	// }

	public void doThrowing(JoinPoint jp, Throwable ex) {
		//获取参数名
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		String[] paramNameArr = methodSignature.getParameterNames();

		//获取参数值
		Object[] paramValueArr = jp.getArgs();
		StringBuilder sbd = new StringBuilder("pparam-->");
		for(int i=0;i<paramValueArr.length;i++){
			sbd.append(paramNameArr[i]+":"+paramValueArr[i]+",");
		}
		logger.error(sbd);
	}
}
