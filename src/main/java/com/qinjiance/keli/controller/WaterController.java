package com.qinjiance.keli.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import module.laohu.commons.model.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qinjiance.keli.constants.Constants;
import com.qinjiance.keli.constants.MessageCode;
import com.qinjiance.keli.manager.IWaterManager;
import com.qinjiance.keli.manager.IWeixinManager;
import com.qinjiance.keli.manager.exception.ManagerException;
import com.qinjiance.keli.model.vo.WaterQPos;

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
public class WaterController extends BaseKeliController {

	@Autowired
	private IWeixinManager WeixinManager;
	@Autowired
	private IWaterManager waterManager;

	/**
	 * 水质
	 * 
	 */
	// @NeedCookie
	@RequestMapping(value = "/getWaterQ")
	public String getWaterQ(String lati, String longi, ModelMap model, HttpServletRequest request) {
		// log记录结果用
		Map<String, Object> resultMap = new HashMap<String, Object>();
		model.put("lati", lati);
		model.put("longi", longi);
		
		try {
			WaterQPos waterQPos = waterManager.getWaterQ(lati, longi, null);
			model.put("waterQPos", waterQPos);
		} catch (ManagerException e) {
			resultMap.put(e.getClass().getSimpleName(), e.getMessage());
		}

		// log记录结果用
		resultMap.putAll(model);
		request.setAttribute(Constants.REQUEST_RETURN_KEY, resultMap);
		return "water/waterQ";
	}

	/**
	 * 水质
	 * 
	 */
	// @NeedCookie
	@RequestMapping(value = "/json/getWaterQ")
	@ResponseBody
	public ResponseResult<WaterQPos> jsonGetWaterQ(String lati, String longi, Integer yinshui,
			HttpServletRequest request) {
		ResponseResult<WaterQPos> rr = new ResponseResult<WaterQPos>();
		rr.setCode(MessageCode.SERVICE_INTERNAL_ERROR.getCode());
		try {
			WaterQPos waterQPos = waterManager.getWaterQ(lati, longi, yinshui);
			rr.setCode(MessageCode.SUCC_0.getCode());
			rr.setResult(waterQPos);
		} catch (ManagerException e) {
			rr.setMessage(e.getMessage());
		}

		// log记录结果用
		request.setAttribute(Constants.REQUEST_RETURN_KEY, rr);
		return rr;
	}

}
