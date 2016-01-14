package com.qinjiance.keli.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qinjiance.keli.constants.Constants;

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

	/**
	 * 首页
	 * 
	 */
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
}
