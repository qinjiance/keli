/**
 * 
 */
package com.qinjiance.keli.util;

import java.lang.annotation.Annotation;

import org.springframework.web.method.HandlerMethod;

/**
 * @author "Jiance Qin"
 * 
 * @date 2014年7月5日
 * 
 * @time 下午5:06:03
 * 
 * @desc
 * 
 */
public class ReflectUtil {

	/**
	 * 获取处理方法的全限定名.
	 * 
	 * @param handler
	 * @return
	 */
	public static String getHandlerFullName(Object handler) {

		StringBuilder fullName = new StringBuilder();

		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			fullName.append(method.getBean().getClass().getName()).append("# ").append(method.getMethod().getName())
					.append(" ()");
		} else {
			fullName.append(handler.getClass().getName());
		}

		return fullName.toString();
	}

	public static String getClassSimpleName(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			return method.getBean().getClass().getSimpleName();
		} else {
			return handler.getClass().getSimpleName();
		}
	}

	/**
	 * 查找处理方法的注解.
	 * 
	 * @param annotation
	 * @param handler
	 * @return
	 */
	public static <T extends Annotation> T findHandlerAnnotation(Class<T> annotation, Object handler) {

		T foundAnnotation = null;

		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			foundAnnotation = method.getBean().getClass().getAnnotation(annotation);
			if (foundAnnotation == null) {
				foundAnnotation = method.getMethodAnnotation(annotation);
			}
		} else {
			foundAnnotation = handler.getClass().getAnnotation(annotation);
		}

		return foundAnnotation;
	}
}
