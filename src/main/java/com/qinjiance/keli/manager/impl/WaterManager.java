/**
 * 
 */
package com.qinjiance.keli.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.qinjiance.keli.constants.Constants;
import com.qinjiance.keli.manager.IWaterManager;
import com.qinjiance.keli.manager.exception.ManagerException;
import com.qinjiance.keli.mapper.WaterQMapper;
import com.qinjiance.keli.mapper.XunbaoMapper;
import com.qinjiance.keli.model.po.Community;
import com.qinjiance.keli.model.po.WaterQ;
import com.qinjiance.keli.model.po.Xunbao;
import com.qinjiance.keli.model.vo.MyWaterQ;
import com.qinjiance.keli.model.vo.WaterMap;
import com.qinjiance.keli.model.vo.WaterQPos;
import com.qinjiance.keli.model.vo.XunbaoResult;

import module.laohu.commons.util.HttpClientUtil;
import module.laohu.commons.util.JsonUtils;

/**
 * @author "Jiance Qin"
 * 
 * @date 2016年1月25日
 * 
 * @time 下午3:00:51
 * 
 * @desc
 * 
 */
@Service
public class WaterManager implements IWaterManager {

	protected final static Logger logger = LoggerFactory.getLogger(WeixinManager.class);

	@Value(value = "#{configProperties['gaode.api.key']}")
	private String GAODE_API_KEY;

	private final static Integer INIT_TOTAL_TIMES = 3;
	private final static Integer MAX_TOTAL_TIMES = 4;
	private final static Integer TOTAL_PARTS = 5;

	private final static String[] LIBAO_NAME = { "小水滴", "水杯", "滤芯", "电源", "底座", "机身" };

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private WaterQMapper waterQMapper;
	@Autowired
	private XunbaoMapper xunbaoMapper;

	/**
	 * 
	 */
	public WaterManager() {
	}

	@Override
	public WaterQPos getWaterQ(String lati, String longi, Integer yinshui, Integer tongzhuangshui, Integer baojie)
			throws ManagerException {
		// 监测点tds
		Community community = getCommunity(lati, longi);
		if (community == null) {
			return null;
		}
		Integer tds = community.getTds();
		if (yinshui != null) {
			switch (yinshui) {
			case 1:
				tds = Long.valueOf(Math.round(tds * 0.1)).intValue();
				break;
			case 2:
				tds = Long.valueOf(Math.round(tds * 0.05)).intValue();
				break;
			default:
				tds = Long.valueOf(Math.round(tds * 0.06)).intValue();
				break;
			}
		} else if (tongzhuangshui != null) {
			switch (tongzhuangshui) {
			case 1:
				tds = Long.valueOf(Math.round(tds * 0.1)).intValue();
				break;
			case 2:
				tds = Long.valueOf(Math.round(tds * 0.3)).intValue();
				break;
			case 3:
				tds = Long.valueOf(Math.round(tds * 0.5)).intValue();
				break;
			default:
				tds = Long.valueOf(Math.round(tds * 0.7)).intValue();
				break;
			}
		} else if (baojie != null) {
			tds = tds * baojie / 10;
		}

		WaterQPos waterQPos = new WaterQPos();
		String posName = getLbsLocationName(lati, longi);
		if (StringUtils.isBlank(posName)) {
			posName = community.getProvince() + community.getCity() + community.getArea() + community.getEstate();
		}
		waterQPos.setPosName(posName);
		waterQPos.setWaterQ(tds);
		waterQPos.setCommunity(community);
		waterQPos.setDesc(getWaterQDesc(tds));
		waterQPos.setDegree(getWaterQDegree(tds));
		return waterQPos;
	}

	@Override
	public MyWaterQ myWaterQ(Long userId, String lati, String longi, String location, Integer yinshui,
			Integer tongzhuangshui, Integer baojie) throws ManagerException {
		WaterQ waterQ = waterQMapper.getByUserId(userId);
		if (waterQ == null) {
			waterQ = new WaterQ();
			waterQ.setLati(lati);
			WaterQPos localWaterQ = getWaterQ(lati, longi, null, null, null);
			waterQ.setLocalWaterQ(localWaterQ.getWaterQ());
			waterQ.setLongi(longi);
			waterQ.setModelId(localWaterQ.getCommunity().getId());
			waterQ.setModelLati(localWaterQ.getCommunity().getLat());
			waterQ.setModelLoca(localWaterQ.getPosName());
			waterQ.setLocation(location);
			waterQ.setModelLongi(localWaterQ.getCommunity().getLng());
			waterQ.setModelWaterQ(localWaterQ.getCommunity().getTds());
			WaterQPos tongWaterQ = getWaterQ(lati, longi, null, tongzhuangshui, null);
			waterQ.setTongWaterQ(tongWaterQ.getWaterQ());
			waterQ.setUserId(userId);
			waterQ.setTongzhuangshui(tongzhuangshui);
			waterQ.setYinshui(yinshui);
			waterQ.setYinshuiji(baojie);
			WaterQPos yinshuijiWaterQ = getWaterQ(lati, longi, null, null, baojie);
			waterQ.setYinshuijiWaterQ(yinshuijiWaterQ.getWaterQ());
			WaterQPos yinshuiWaterQ = getWaterQ(lati, longi, yinshui, null, null);
			waterQ.setYinshuiWaterQ(yinshuiWaterQ.getWaterQ());
			waterQ.setZongheWaterQ(waterQ.getLocalWaterQ() + waterQ.getTongWaterQ() + waterQ.getYinshuiWaterQ()
					+ waterQ.getYinshuijiWaterQ());
			Integer ret = waterQMapper.insert(waterQ);
			if (ret == null || ret != 1) {
				throw new ManagerException("保存数据失败，请重试");
			}
		}
		MyWaterQ myWaterQ = new MyWaterQ();
		myWaterQ.setLoca(waterQ.getModelLoca() + " " + waterQ.getLocation());
		myWaterQ.setWaterQ(waterQ.getZongheWaterQ());
		myWaterQ.setWaterQDesc(getWaterQDesc(myWaterQ.getWaterQ()));
		myWaterQ.setWaterQDegree(getWaterQDegree(myWaterQ.getWaterQ()));
		myWaterQ.setLocalWaterQDesc(getWaterQDesc(waterQ.getLocalWaterQ()));
		myWaterQ.setLocalWaterQDegree(getWaterQDegree(waterQ.getLocalWaterQ()));
		myWaterQ.setYinshuiWaterQDesc(getWaterQDesc(waterQ.getYinshuiWaterQ()));
		myWaterQ.setYinshuiWaterQDegree(getWaterQDegree(waterQ.getYinshuiWaterQ()));
		myWaterQ.setTongWaterQDesc(getWaterQDesc(waterQ.getTongWaterQ()));
		myWaterQ.setTongWaterQDegree(getWaterQDegree(waterQ.getTongWaterQ()));
		myWaterQ.setYinshuijiWaterQDesc(getWaterQDesc(waterQ.getYinshuijiWaterQ()));
		myWaterQ.setYinshuijiWaterQDegree(getWaterQDegree(waterQ.getYinshuijiWaterQ()));
		return myWaterQ;
	}

	public String getWaterQDesc(Integer waterQ) {
		if (waterQ <= 100) {
			return "优";
		} else if (waterQ <= 500) {
			return "良";
		} else {
			return "差";
		}
	}

	public String getWaterQDegree(Integer waterQ) {
		if (waterQ <= 100) {
			return "gr";
		} else if (waterQ <= 500) {
			return "blue";
		} else {
			return "or";
		}
	}

	@Override
	public MyWaterQ getMyWaterQ(Long userId) throws ManagerException {
		WaterQ waterQ = waterQMapper.getByUserId(userId);
		if (waterQ == null) {
			return null;
		}
		MyWaterQ myWaterQ = new MyWaterQ();
		myWaterQ.setLoca(waterQ.getModelLoca() + " " + waterQ.getLocation());
		myWaterQ.setWaterQ(waterQ.getZongheWaterQ());
		myWaterQ.setWaterQDesc(getWaterQDesc(myWaterQ.getWaterQ()));
		myWaterQ.setWaterQDegree(getWaterQDegree(myWaterQ.getWaterQ()));
		myWaterQ.setLocalWaterQDesc(getWaterQDesc(waterQ.getLocalWaterQ()));
		myWaterQ.setLocalWaterQDegree(getWaterQDegree(waterQ.getLocalWaterQ()));
		myWaterQ.setYinshuiWaterQDesc(getWaterQDesc(waterQ.getYinshuiWaterQ()));
		myWaterQ.setYinshuiWaterQDegree(getWaterQDegree(waterQ.getYinshuiWaterQ()));
		myWaterQ.setTongWaterQDesc(getWaterQDesc(waterQ.getTongWaterQ()));
		myWaterQ.setTongWaterQDegree(getWaterQDegree(waterQ.getTongWaterQ()));
		myWaterQ.setYinshuijiWaterQDesc(getWaterQDesc(waterQ.getYinshuijiWaterQ()));
		myWaterQ.setYinshuijiWaterQDegree(getWaterQDegree(waterQ.getYinshuijiWaterQ()));
		return myWaterQ;
	}

	@Override
	public Community getCommunity(String lati, String longi) throws ManagerException {
		String collectionName = "community";
		GeoResults<Community> cc = mongoTemplate.geoNear(
				NearQuery.near(Double.valueOf(longi), Double.valueOf(lati)).num(1), Community.class, collectionName);
		if (cc == null || cc.getContent() == null || cc.getContent().isEmpty()) {
			return mongoTemplate.findOne(new Query(), Community.class, collectionName);
		}
		return cc.getContent().get(0).getContent();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getLbsLocationName(String lati, String longi) throws ManagerException {
		String url = "http://restapi.amap.com/v3/geocode/regeo?key=" + GAODE_API_KEY + "&location=" + longi + "," + lati
				+ "&poitype=&radius=0&extensions=base&batch=false&roadlevel=1";
		String response = null;
		try {
			response = HttpClientUtil.invokeGet(url, new HashMap<String, String>(), Constants.CHARSET,
					Constants.HTTP_TIMEOUT, Constants.SO_TIMEOUT);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return null;
		}
		if (StringUtils.isBlank(response)) {
			logger.error("查询地理位置错误，请稍后再试");
			return null;
		}
		Map<String, Object> result = JsonUtils.parse(response);
		if (result == null || result.isEmpty() || !result.get("status").equals("1")
				|| result.get("regeocode") == null) {
			logger.error("查询地理位置失败，请稍后再试");
			return null;
		}
		return (String) ((Map<String, Object>) result.get("regeocode")).get("formatted_address");
	}

	@Override
	public WaterMap waterMap(Long userId) throws ManagerException {
		WaterQ waterQ = waterQMapper.getByUserId(userId);
		if (waterQ == null) {
			return null;
		}
		WaterMap waterMap = new WaterMap();
		waterMap.setLat(waterQ.getLati());
		waterMap.setLng(waterQ.getLongi());
		waterMap.setErCiLv(waterQ.getZongheWaterQ() / 10);
		waterMap.setJieGouLv(waterQ.getZongheWaterQ() / 15);
		waterMap.setXiJunLv(waterQ.getZongheWaterQ() / 20);

		Xunbao xunbao = xunbaoMapper.getByUserId(userId);
		if (xunbao == null) {
			xunbao = new Xunbao();
			xunbao.setCurrTimes(3);
			xunbao.setLastModifyDate(new Date());
			xunbao.setPart1(0);
			xunbao.setPart2(0);
			xunbao.setPart3(0);
			xunbao.setPart4(0);
			xunbao.setPart5(0);
			xunbao.setShuidi(0);
			xunbao.setTodayTotalTimes(INIT_TOTAL_TIMES);
			xunbao.setUserId(userId);
			xunbaoMapper.insert(xunbao);
		}
		Integer currParts = 0;
		if (xunbao.getPart1() > 0) {
			currParts++;
		}
		if (xunbao.getPart2() > 0) {
			currParts++;
		}
		if (xunbao.getPart3() > 0) {
			currParts++;
		}
		if (xunbao.getPart4() > 0) {
			currParts++;
		}
		if (xunbao.getPart5() > 0) {
			currParts++;
		}
		waterMap.setCurrParts(currParts);
		waterMap.setShuidi(xunbao.getShuidi());
		Integer todayTotalTimes = xunbao.getTodayTotalTimes();
		Integer currTimes = xunbao.getCurrTimes();
		Date lastModTime = xunbao.getLastModifyDate();
		lastModTime = DateUtils.truncate(lastModTime, Calendar.DATE);
		if (lastModTime.before(DateUtils.truncate(new Date(), Calendar.DATE))) {
			todayTotalTimes = INIT_TOTAL_TIMES;
			currTimes = INIT_TOTAL_TIMES;
		}
		waterMap.setTodayTotalTimes(todayTotalTimes);
		waterMap.setCurrTimes(currTimes);
		waterMap.setTotalParts(TOTAL_PARTS);
		return waterMap;
	}

	@Override
	public XunbaoResult xunbao(Long userId) throws ManagerException {
		Xunbao xunbao = xunbaoMapper.getByUserId(userId);
		if (xunbao == null) {
			xunbao = new Xunbao();
			xunbao.setCurrTimes(3);
			xunbao.setLastModifyDate(new Date());
			xunbao.setPart1(0);
			xunbao.setPart2(0);
			xunbao.setPart3(0);
			xunbao.setPart4(0);
			xunbao.setPart5(0);
			xunbao.setShuidi(0);
			xunbao.setTodayTotalTimes(INIT_TOTAL_TIMES);
			xunbao.setUserId(userId);
			xunbaoMapper.insert(xunbao);
		}
		Integer shuidi = xunbao.getShuidi();
		Integer currParts = 0;
		if (xunbao.getPart1() > 0) {
			currParts++;
		}
		if (xunbao.getPart2() > 0) {
			currParts++;
		}
		if (xunbao.getPart3() > 0) {
			currParts++;
		}
		if (xunbao.getPart4() > 0) {
			currParts++;
		}
		if (xunbao.getPart5() > 0) {
			currParts++;
		}
		Integer todayTotalTimes = xunbao.getTodayTotalTimes();
		Integer currTimes = xunbao.getCurrTimes();
		Date lastModTime = xunbao.getLastModifyDate();
		lastModTime = DateUtils.truncate(lastModTime, Calendar.DATE);
		if (lastModTime.before(DateUtils.truncate(new Date(), Calendar.DATE))) {
			todayTotalTimes = INIT_TOTAL_TIMES;
			currTimes = INIT_TOTAL_TIMES;
		}
		if (currTimes <= 0) {
			throw new ManagerException("当天寻宝次数已用尽");
		}
		// 当天已获得宝物
		Integer roll = 100;
		Date lastGetDate = xunbao.getLastGetDate();
		if (lastGetDate == null || DateUtils.truncate(lastGetDate, Calendar.DATE)
				.before(DateUtils.truncate(new Date(), Calendar.DATE))) {
			roll = new Random().nextInt(10);
		}
		Integer updateRet = null;
		boolean getLibao = false;
		if (roll == 0) {
			updateRet = xunbaoMapper.updateShuidi(userId);
			shuidi++;
			getLibao = true;
		} else if (roll == 1) {
			updateRet = xunbaoMapper.updatePart1(userId);
			if (xunbao.getPart1() == 0) {
				currParts++;
			}
			getLibao = true;
		} else if (roll == 2) {
			updateRet = xunbaoMapper.updatePart2(userId);
			if (xunbao.getPart2() == 0) {
				currParts++;
			}
			getLibao = true;
		} else if (roll == 3) {
			updateRet = xunbaoMapper.updatePart3(userId);
			if (xunbao.getPart3() == 0) {
				currParts++;
			}
			getLibao = true;
		} else if (roll == 4) {
			updateRet = xunbaoMapper.updatePart4(userId);
			if (xunbao.getPart4() == 0) {
				currParts++;
			}
			getLibao = true;
		} else if (roll == 5) {
			// 永远不可以获得5 updateRet = xunbaoMapper.updatePart5(userId);
			updateRet = xunbaoMapper.updateCurr(userId);
		} else {
			updateRet = xunbaoMapper.updateCurr(userId);
		}
		if (updateRet == null || updateRet != 1) {
			throw new ManagerException("更新失败");
		}

		XunbaoResult result = new XunbaoResult();
		result.setCurrParts(currParts);
		result.setCurrTimes(currTimes - 1);
		if (getLibao) {
			result.setLibaoName(LIBAO_NAME[roll]);
			result.setLibaoType(roll);
		}
		result.setShuidi(shuidi);
		result.setTodayTotalTimes(todayTotalTimes);
		result.setTotalParts(TOTAL_PARTS);
		return result;
	}

}
