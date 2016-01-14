package com.qinjiance.keli.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.laohu.commons.model.ResponseResult;
import module.laohu.commons.util.CheckStyleUtil;
import module.laohu.commons.util.JsonUtils;
import module.laohu.commons.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.qinjiance.keli.annotation.NeedCookie;
import com.qinjiance.keli.annotation.SkipWhenUserLogin;
import com.qinjiance.keli.constants.Constants;
import com.qinjiance.keli.constants.MessageCode;
import com.qinjiance.keli.util.CookieUtil;
import com.qinjiance.keli.util.ReflectUtil;

/**
 * 用户web登录检查拦截器
 */
public class LoginRequiredInterceptor implements HandlerInterceptor {

	private final static Logger logger = LoggerFactory.getLogger(LoginRequiredInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelandview) throws Exception {
		// 请求及响应日志
		requestHandleLog(request, handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 业务处理开始时间
		long requestStartTime = System.currentTimeMillis();
		request.setAttribute(Constants.REQUEST_START_TIME_KEY, requestStartTime);

		String requestPath = request.getRequestURI();
		// 浏览器登录态验证.
		NeedCookie needCookie = ReflectUtil.findHandlerAnnotation(NeedCookie.class, handler);
		// 有，则检测根域名下login cookie是否有效.
		if (needCookie != null && needCookie.isNeed()) {
			if (isLogonCookieValid(request, response)) {
				return true;
			} else {
				// 请求及响应日志
				request.setAttribute(Constants.REQUEST_RETURN_KEY, "Web request path: " + requestPath
						+ ", invalid login cookie, redirect to login.");
				requestHandleLog(request, handler);

				// 无效，则跳转用户登录页面.
				String redirectUrl = "/loginPage?location=" + getRequestLocation(request, false);
				redirect(redirectUrl, request, response);
				return false;
			}
		}

		// 如果用户已登录则跳过这些页面
		SkipWhenUserLogin skipWhenUserLogin = ReflectUtil.findHandlerAnnotation(SkipWhenUserLogin.class, handler);
		// 有，则检测根域名下logon cookie是否有效.
		if (skipWhenUserLogin != null && skipWhenUserLogin.isNeed()) {
			if (isLogonCookieValid(request, response)) {
				// 有效，则跳转location指定的页面，如果没有locatin，则跳转到首页.
				String location = request.getParameter("location");
				if (StringUtils.isNotBlank(location)) {
					try {
						location = URLDecoder.decode(location, Constants.CHARSET);
					} catch (UnsupportedEncodingException e) {
						logger.error("UnsupportedEncodingException: ", e);
					}
				}
				if (!CheckStyleUtil.checkStyle(location, CheckStyleUtil.PATTERN_URL)) {
					location = getBasePath(request);
				}

				// 请求及响应日志
				request.setAttribute(Constants.REQUEST_RETURN_KEY, "Web request path: " + requestPath
						+ ", valid cookie, redirect to: " + location);
				requestHandleLog(request, handler);

				redirect(location, request, response);
				return false;
			} else {
				return true;
			}
		}

		return true;
	}

	/**
	 * 登录cookie是否有效.
	 * 
	 * @param request
	 * @return
	 */
	private boolean isLogonCookieValid(HttpServletRequest request, HttpServletResponse response) {

		Long userId = CookieUtil.getUserIdFromCookie();
		if (userId != null && userId.longValue() > 0) {
			logger.info("Web request path: " + request.getRequestURI() + ", found userId " + userId.toString());
			CookieUtil.updateExpire(response, CookieUtil.COOKIE_LIVE_10_DAYS);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 判断是否为get请求
	 * 
	 * @param request
	 * @return
	 */
	private boolean isGetRequest(HttpServletRequest request) {

		String method = request.getMethod();
		return StringUtils.isNotBlank(method) && method.equalsIgnoreCase("get");
	}

	/**
	 * 获取请求地址，用于登录后的重定向，只获取get请求的地址和参数，其他方式的请求则忽略到主页.
	 * 
	 * @return
	 */
	private String getRequestLocation(HttpServletRequest request, boolean isHttps) {

		String requestAddr = isHttps ? getHttpsBasePath(request) : getBasePath(request);

		if (isGetRequest(request)) {
			String uri = request.getRequestURI();
			if (StringUtils.isBlank(uri)) {
				uri = "";
			}
			String queryString = request.getQueryString();
			if (StringUtils.isNotBlank(queryString)) {
				uri += "?" + queryString;
			}
			requestAddr += uri;
		}
		try {
			return URLEncoder.encode(requestAddr, Constants.CHARSET);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException: ", e);
		}
		return "/";
	}

	/**
	 * 获取本站的根路径URL
	 * 
	 * @return
	 */
	private String getBasePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName()
				+ (request.getServerPort() == 80 ? "" : (":" + request.getServerPort())) + request.getContextPath();
	}

	/**
	 * 获取本站的https根路径URL
	 * 
	 * @return
	 */
	private String getHttpsBasePath(HttpServletRequest request) {
		return "https://" + request.getServerName()
				+ (request.getServerPort() == 80 ? "" : (":" + request.getServerPort())) + request.getContextPath();
	}

	/**
	 * 获取请求参数串
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String getRequestParamsStr(HttpServletRequest request) {
		String ret = "";
		Map<String, Object> srcMap = request.getParameterMap();
		Map<String, Object> desMap = new HashMap<String, Object>();
		if (srcMap != null && !srcMap.isEmpty()) {
			String[] values;
			for (Entry<String, Object> entry : srcMap.entrySet()) {
				values = (String[]) entry.getValue();
				if (values != null && values.length > 1) {
					desMap.put(entry.getKey(), values);
				} else {
					desMap.put(entry.getKey(), values[0]);
				}
			}
			ret = JsonUtils.objectToJson(desMap);
		}
		return ret;
	}

	/**
	 * 请求响应日志
	 * 
	 * @param request
	 * @param handler
	 */
	@SuppressWarnings("rawtypes")
	protected static void requestHandleLog(HttpServletRequest request, Object handler) {
		// 业务处理完成时间
		Object requestStartTimeObj = request.getAttribute(Constants.REQUEST_START_TIME_KEY);
		long requestStartTime = requestStartTimeObj == null ? System.currentTimeMillis() : (Long) requestStartTimeObj;
		long requestEndTime = System.currentTimeMillis();
		long duration = requestEndTime - requestStartTime;
		// 业务处理结果，在业务方法中已放出request里
		Object returnObj = request.getAttribute(Constants.REQUEST_RETURN_KEY);
		// 返回值转换
		String returnStr = "";
		if (returnObj != null && !((returnObj instanceof Map) && (((Map) returnObj).isEmpty()))) {
			returnStr = JsonUtils.objectToJson(returnObj);
		}
		if (returnStr.length() > 500) {
			returnStr = returnStr.substring(0, 500) + "...";
		}
		// 调试log
		StringBuilder log = new StringBuilder();
		log.append(ReflectUtil.getHandlerFullName(handler)).append(", requestParams=")
				.append(getRequestParamsStr(request)).append(", result=").append(returnStr).append(", requestIp=")
				.append(WebUtils.getIpAddress(request)).append(", handleTime=").append(duration).append("ms");
		logger.info(hideSecretLog(log.toString()));
	}

	protected static String hideSecretLog(String log) {
		List<String> secretKey = new ArrayList<String>();
		secretKey.add("password");
		secretKey.add("rePassword");
		String newLog = log;
		StringBuilder regSb = new StringBuilder();
		StringBuilder replaceSb = new StringBuilder();
		for (String key : secretKey) {
			regSb.append("\"").append(key).append("\":\".*?\"");
			replaceSb.append("\"").append(key).append("\":\"***\"");
			newLog = newLog.replaceAll(regSb.toString(), replaceSb.toString());
			regSb.delete(0, regSb.length());
			replaceSb.delete(0, replaceSb.length());
		}
		return newLog;
	}

	/**
	 * 根据请求的期望返回值类型进行重定向
	 * 
	 * @param redirectUrl
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void redirect(String redirectUrl, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String accept = request.getHeader("Accept");
		if (StringUtils.isNotBlank(accept) && accept.contains("application/json")) {// json格式重定向
			ResponseResult<String> rr = new ResponseResult<String>();
			rr.setCode(MessageCode.HTTP_REDIRECT.getCode());
			rr.setMessage(MessageCode.HTTP_REDIRECT.getMessage());
			rr.setResult(redirectUrl);

			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(JsonUtils.objectToJson(rr));
			response.getWriter().flush();
			response.getWriter().close();
		} else if (StringUtils.isNotBlank(accept) && accept.contains("text/javascript")
				&& accept.contains("application/javascript")) {
			String callback = request.getParameter("callback");
			if (StringUtils.isNotBlank(callback)) {
				StringBuilder jsonp = new StringBuilder(HtmlUtils.htmlEscape(callback));
				ResponseResult<String> rr = new ResponseResult<String>();
				rr.setCode(MessageCode.HTTP_REDIRECT.getCode());
				rr.setMessage(MessageCode.HTTP_REDIRECT.getMessage());
				rr.setResult(redirectUrl);
				jsonp.append("('").append(JsonUtils.objectToJson(rr)).append("')");

				response.setContentType("text/javascript;charset=UTF-8");
				response.getWriter().print(jsonp.toString());
				response.getWriter().flush();
				response.getWriter().close();
			}
		} else {
			response.sendRedirect(redirectUrl);
		}
	}
}
