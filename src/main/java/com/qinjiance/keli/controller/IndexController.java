package com.qinjiance.keli.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.laohu.commons.util.CheckStyleUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qinjiance.keli.annotation.SkipWhenUserLogin;
import com.qinjiance.keli.constants.Constants;
import com.qinjiance.keli.manager.IWeixinManager;
import com.qinjiance.keli.manager.exception.ManagerException;

/**
 * @author "Jiance Qin"
 * 
 * @date 2014年7月5日
 * 
 * @time 下午4:55:00
 * 
 * @desc
 * 
 */
@Controller
public class IndexController extends BaseKeliController {

	@Autowired
	private IWeixinManager WeixinManager;

	/**
	 * 首页
	 * 
	 */
	// @NeedCookie
	@RequestMapping(value = { "/", "/index" })
	public String index(ModelMap model, HttpServletRequest request) {
		// log记录结果用
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// try {
		//
		//
		// } catch (ManagerException e) {
		// resultMap.put(e.getClass().getSimpleName(), e.getMessage());
		// }

		// log记录结果用
		resultMap.putAll(model);
		request.setAttribute(Constants.REQUEST_RETURN_KEY, resultMap);
		return "index";
	}

	@SkipWhenUserLogin
	@RequestMapping(value = "/loginPage")
	public String loginPage(String location, HttpServletRequest request) {
		// log记录结果用
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String weixinOAuthUrl = "";
		try {
			if (StringUtils.isBlank(location) || !CheckStyleUtil.checkStyle(location, CheckStyleUtil.PATTERN_URL)) {
				location = "/";
			}
			resultMap.put("location", location);

			weixinOAuthUrl = WeixinManager.getWeixinPublicOAuthUrl(location);
			resultMap.put("weixinOAuthUrl", weixinOAuthUrl);
		} catch (ManagerException e) {
			resultMap.put(e.getClass().getSimpleName(), e.getMessage());
		}

		// log记录结果用
		request.setAttribute(Constants.REQUEST_RETURN_KEY, resultMap);
		return REDIRECT + weixinOAuthUrl;
	}

	@SkipWhenUserLogin
	@RequestMapping(value = "/weixinPublicOAuth")
	public String weixinPublicOAuth(String code, @RequestParam String state, HttpServletRequest request,
			HttpServletResponse response) {
		// log记录结果用
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String loginRedirect = "";
		try {
			if (StringUtils.isNotBlank(code)) {
				String location = WeixinManager.weixinPublicOAuthLogin(code, state, response);
				if (StringUtils.isNotBlank(location)) {
					loginRedirect = location;
				}
			}
			resultMap.put("loginRedirect", loginRedirect);
		} catch (ManagerException e) {
			resultMap.put(e.getClass().getSimpleName(), e.getMessage());
		}

		// log记录结果用
		request.setAttribute(Constants.REQUEST_RETURN_KEY, resultMap);
		return REDIRECT + loginRedirect;
	}
}