package com.qinjiance.keli.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qinjiance.keli.annotation.NeedCookie;
import com.qinjiance.keli.constants.Constants;
import com.qinjiance.keli.constants.MessageCode;
import com.qinjiance.keli.manager.IWaterManager;
import com.qinjiance.keli.manager.exception.ManagerException;
import com.qinjiance.keli.model.vo.MyWaterQ;
import com.qinjiance.keli.model.vo.MyWaterQSimple;
import com.qinjiance.keli.model.vo.PkResult;
import com.qinjiance.keli.model.vo.UserPosition;
import com.qinjiance.keli.model.vo.WaterMap;
import com.qinjiance.keli.model.vo.WaterQPos;
import com.qinjiance.keli.model.vo.XunbaoResult;
import com.qinjiance.keli.util.CookieUtil;

import module.laohu.commons.model.ResponseCode;
import module.laohu.commons.model.ResponseResult;

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
	private IWaterManager waterManager;

	/**
	 * 水质
	 * 
	 */
	@NeedCookie
	@RequestMapping(value = "/getWaterQ")
	public String getWaterQ(String lati, String longi, ModelMap model, HttpServletRequest request) {
		// log记录结果用
		Map<String, Object> resultMap = new HashMap<String, Object>();
		model.put("lati", lati);
		model.put("longi", longi);
		MyWaterQ myWaterQ = null;
		try {
			myWaterQ = waterManager.getMyWaterQ(CookieUtil.getUserIdFromCookie());
			if (myWaterQ == null) {
				WaterQPos waterQPos = waterManager.getWaterQ(lati, longi, null, null, null);
				model.put("waterQPos", waterQPos);
			} else {
				model.put("myWaterQ", myWaterQ);
			}
		} catch (ManagerException e) {
			resultMap.put(e.getClass().getSimpleName(), e.getMessage());
		}

		// log记录结果用
		resultMap.putAll(model);
		request.setAttribute(Constants.REQUEST_RETURN_KEY, resultMap);
		if (myWaterQ == null) {
			return "water/waterQ";
		} else {
			return "water/myWaterQ";
		}
	}

	/**
	 * 水质
	 * 
	 */
	@NeedCookie
	@RequestMapping(value = "/json/getWaterQ")
	@ResponseBody
	public ResponseResult<WaterQPos> jsonGetWaterQ(String lati, String longi, Integer yinshui, Integer tongzhuangshui,
			Integer baojie, HttpServletRequest request) {
		ResponseResult<WaterQPos> rr = new ResponseResult<WaterQPos>();
		rr.setCode(MessageCode.SERVICE_INTERNAL_ERROR.getCode());
		try {
			WaterQPos waterQPos = waterManager.getWaterQ(lati, longi, yinshui, tongzhuangshui, baojie);
			rr.setCode(MessageCode.SUCC_0.getCode());
			rr.setResult(waterQPos);
		} catch (ManagerException e) {
			rr.setMessage(e.getMessage());
		}

		// log记录结果用
		request.setAttribute(Constants.REQUEST_RETURN_KEY, rr);
		return rr;
	}

	/**
	 * 水质
	 * 
	 */
	@NeedCookie
	@RequestMapping(value = "/myWaterQ")
	public String myWaterQ(String lati, String longi, String location, Integer yinshui, Integer tongzhuangshui,
			Integer baojie, ModelMap model, HttpServletRequest request) {
		// log记录结果用
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			MyWaterQ myWaterQ = waterManager.myWaterQ(CookieUtil.getUserIdFromCookie(), lati, longi, location, yinshui,
					tongzhuangshui, baojie);
			model.put("myWaterQ", myWaterQ);
		} catch (ManagerException e) {
			resultMap.put(e.getClass().getSimpleName(), e.getMessage());
		}

		// log记录结果用
		resultMap.putAll(model);
		request.setAttribute(Constants.REQUEST_RETURN_KEY, resultMap);
		return "water/myWaterQ";
	}

	@NeedCookie
	@RequestMapping(value = "/waterMap")
	public String waterMap(ModelMap model, HttpServletRequest request) {
		// log记录结果用
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			WaterMap waterMap = waterManager.waterMap(CookieUtil.getUserIdFromCookie());
			model.put("waterMap", waterMap);
		} catch (ManagerException e) {
			resultMap.put(e.getClass().getSimpleName(), e.getMessage());
		}

		// log记录结果用
		resultMap.putAll(model);
		request.setAttribute(Constants.REQUEST_RETURN_KEY, resultMap);
		return "water/waterMap";
	}

	@NeedCookie
	@RequestMapping(value = "/xunbao")
	@ResponseBody
	public ResponseResult<XunbaoResult> xunbao(HttpServletRequest request) {
		ResponseResult<XunbaoResult> rr = new ResponseResult<XunbaoResult>();
		rr.setCode(MessageCode.SERVICE_INTERNAL_ERROR.getCode());
		try {
			XunbaoResult xunbaoResult = waterManager.xunbao(CookieUtil.getUserIdFromCookie());
			if (xunbaoResult.getLibaoType() == null) {
				rr.setCode(MessageCode.SUCC_1.getCode());
			} else {
				rr.setCode(MessageCode.SUCC_0.getCode());
			}
			rr.setResult(xunbaoResult);
		} catch (ManagerException e) {
			rr.setMessage(e.getMessage());
		}

		// log记录结果用
		request.setAttribute(Constants.REQUEST_RETURN_KEY, rr);
		return rr;
	}

	@NeedCookie
	@RequestMapping(value = "/pkAround")
	public String pkAround(ModelMap model, HttpServletRequest request) {
		// log记录结果用
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Long userId = CookieUtil.getUserIdFromCookie();
			MyWaterQSimple myWaterQ = waterManager.getMyWaterQSimple(userId);
			model.put("myWaterQ", myWaterQ);

			List<UserPosition> userPositions = waterManager.getAroundUser(userId);
			model.put("userPositions", userPositions);
		} catch (ManagerException e) {
			resultMap.put(e.getClass().getSimpleName(), e.getMessage());
		}

		// log记录结果用
		resultMap.putAll(model);
		request.setAttribute(Constants.REQUEST_RETURN_KEY, resultMap);
		return "water/pkAround";
	}

	@NeedCookie
	@RequestMapping(value = "/pkCity")
	public String pkCity(ModelMap model, HttpServletRequest request) {
		// log记录结果用
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Long userId = CookieUtil.getUserIdFromCookie();
			MyWaterQSimple myWaterQ = waterManager.getMyWaterQSimple(userId);
			model.put("myWaterQ", myWaterQ);

			List<String> cities = waterManager.getCities();
			model.put("cities", cities);

			List<UserPosition> userPositions = waterManager.getUsersByUserCity(userId, myWaterQ.getCity());
			model.put("userPositions", userPositions);
		} catch (ManagerException e) {
			resultMap.put(e.getClass().getSimpleName(), e.getMessage());
		}

		// log记录结果用
		resultMap.putAll(model);
		request.setAttribute(Constants.REQUEST_RETURN_KEY, resultMap);
		return "water/pkCity";
	}

	@NeedCookie
	@RequestMapping(value = "/findPkByCity")
	@ResponseBody
	public ResponseResult<List<UserPosition>> findPkByCity(@RequestParam String city, HttpServletRequest request) {
		ResponseResult<List<UserPosition>> rr = new ResponseResult<List<UserPosition>>();
		rr.setCode(MessageCode.SERVICE_INTERNAL_ERROR.getCode());
		try {
			List<UserPosition> userPositions = waterManager.getUsersByUserCity(CookieUtil.getUserIdFromCookie(), city);
			rr.setCode(MessageCode.SUCC_0.getCode());
			rr.setResult(userPositions);
		} catch (ManagerException e) {
			rr.setMessage(e.getMessage());
		}

		// log记录结果用
		request.setAttribute(Constants.REQUEST_RETURN_KEY, rr);
		return rr;
	}

	@NeedCookie
	@RequestMapping(value = "/pk")
	@ResponseBody
	public ResponseResult<PkResult> pk(@RequestParam Long pkUserId, HttpServletRequest request) {
		ResponseResult<PkResult> rr = new ResponseResult<PkResult>();
		rr.setCode(MessageCode.SERVICE_INTERNAL_ERROR.getCode());
		try {
			PkResult pkResult = waterManager.pk(CookieUtil.getUserIdFromCookie(), pkUserId);
			rr.setCode(MessageCode.SUCC_0.getCode());
			rr.setResult(pkResult);
		} catch (ManagerException e) {
			rr.setMessage(e.getMessage());
		}

		// log记录结果用
		request.setAttribute(Constants.REQUEST_RETURN_KEY, rr);
		return rr;
	}

	@NeedCookie
	@RequestMapping(value = "/finishedShare")
	@ResponseBody
	public ResponseCode finishedShare(@RequestParam String signature, HttpServletRequest request) {
		ResponseCode rc = new ResponseCode();
		rc.setCode(MessageCode.SERVICE_INTERNAL_ERROR.getCode());
		try {
			boolean ret = waterManager.finishedShare(CookieUtil.getUserIdFromCookie(), signature);
			if (ret) {
				rc.setCode(MessageCode.SUCC_0.getCode());
			}
		} catch (ManagerException e) {
			rc.setMessage(e.getMessage());
		}

		// log记录结果用
		request.setAttribute(Constants.REQUEST_RETURN_KEY, rc);
		return rc;
	}
}
