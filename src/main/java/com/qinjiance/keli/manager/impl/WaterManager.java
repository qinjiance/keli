/**
 * 
 */
package com.qinjiance.keli.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.qinjiance.keli.manager.impl.EhCacheManager.CacheType;
import com.qinjiance.keli.mapper.WaterQMapper;
import com.qinjiance.keli.mapper.WeixinThirdUserMapper;
import com.qinjiance.keli.mapper.XunbaoMapper;
import com.qinjiance.keli.model.po.Community;
import com.qinjiance.keli.model.po.WaterQ;
import com.qinjiance.keli.model.po.WeixinThirdUser;
import com.qinjiance.keli.model.po.Xunbao;
import com.qinjiance.keli.model.vo.MyWaterQ;
import com.qinjiance.keli.model.vo.MyWaterQSimple;
import com.qinjiance.keli.model.vo.PkResult;
import com.qinjiance.keli.model.vo.UserPosition;
import com.qinjiance.keli.model.vo.WaterMap;
import com.qinjiance.keli.model.vo.WaterQPos;
import com.qinjiance.keli.model.vo.XunbaoResult;
import com.qinjiance.keli.util.CookieUtil;

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

	private static final double R = 6378137; // 地球半径
	private static final double DEGREE = (24901 * 1609) / 360.0;
	private static final double PI_DIV = Math.PI / 180.0;

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
	@Autowired
	private WeixinThirdUserMapper weixinThirdUserMapper;
	@Autowired
	private EhCacheManager ehCacheManager;

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
			waterQ.setCity(getLbsCityName(lati, longi));
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

	@SuppressWarnings("unchecked")
	protected String getLbsCityName(String lati, String longi) throws ManagerException {
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
		Object city = ((Map<String, Object>) ((Map<String, Object>) result.get("regeocode")).get("addressComponent"))
				.get("city");
		if (city != null && city instanceof String) {
			return (String) city;
		} else {
			return (String) ((Map<String, Object>) ((Map<String, Object>) result.get("regeocode"))
					.get("addressComponent")).get("province");
		}
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
			xunbao.setCurrTimes(INIT_TOTAL_TIMES);
			xunbao.setLastModifyDate(new Date());
			xunbao.setPart1(0);
			xunbao.setPart2(0);
			xunbao.setPart3(0);
			xunbao.setPart4(0);
			xunbao.setPart5(0);
			xunbao.setShuidi(0);
			xunbao.setTodayTotalTimes(INIT_TOTAL_TIMES);
			xunbao.setUserId(userId);
			Integer insertRet = xunbaoMapper.insert(xunbao);
			if (insertRet == null || insertRet != 1) {
				throw new ManagerException("初始化寻宝数据失败");
			}
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
		currTimes--;
		Integer updateRet = null;
		boolean getLibao = false;
		if (roll == 0) {
			updateRet = xunbaoMapper.updateShuidi(userId, currTimes, todayTotalTimes);
			shuidi++;
			getLibao = true;
		} else if (roll == 1) {
			updateRet = xunbaoMapper.updatePart1(userId, currTimes, todayTotalTimes);
			if (xunbao.getPart1() == 0) {
				currParts++;
			}
			getLibao = true;
		} else if (roll == 2) {
			updateRet = xunbaoMapper.updatePart2(userId, currTimes, todayTotalTimes);
			if (xunbao.getPart2() == 0) {
				currParts++;
			}
			getLibao = true;
		} else if (roll == 3) {
			updateRet = xunbaoMapper.updatePart3(userId, currTimes, todayTotalTimes);
			if (xunbao.getPart3() == 0) {
				currParts++;
			}
			getLibao = true;
		} else if (roll == 4) {
			updateRet = xunbaoMapper.updatePart4(userId, currTimes, todayTotalTimes);
			if (xunbao.getPart4() == 0) {
				currParts++;
			}
			getLibao = true;
		} else if (roll == 5) {
			// 永远不可以获得5 updateRet = xunbaoMapper.updatePart5(userId);
			updateRet = xunbaoMapper.updateCurr(userId, currTimes, todayTotalTimes);
		} else {
			updateRet = xunbaoMapper.updateCurr(userId, currTimes, todayTotalTimes);
		}
		if (updateRet == null || updateRet != 1) {
			throw new ManagerException("更新失败");
		}

		XunbaoResult result = new XunbaoResult();
		result.setCurrParts(currParts);
		result.setCurrTimes(currTimes);
		if (getLibao) {
			result.setLibaoName(LIBAO_NAME[roll]);
			result.setLibaoType(roll);
		}
		result.setShuidi(shuidi);
		result.setTodayTotalTimes(todayTotalTimes);
		result.setTotalParts(TOTAL_PARTS);
		return result;
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	protected int getDistance(double long1, double lat1, double long2, double lat2) {
		double a, b;
		lat1 = lat1 * PI_DIV;
		lat2 = lat2 * PI_DIV;
		a = lat1 - lat2;
		b = (long1 - long2) * PI_DIV;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		return (int) (2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2)));
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	protected int getDistance(double long1, double lat1, Long userId) {
		WaterQ waterQ = waterQMapper.getByUserId(userId);
		if (waterQ != null) {
			return this.getDistance(long1, lat1, Double.valueOf(waterQ.getLongi()), Double.valueOf(waterQ.getLati()));
		}
		return 0;
	}

	/**
	 * 生成以中心点为中心的四方形经纬度
	 * 
	 * @param lon
	 *            精度
	 * @param lat
	 *            纬度
	 * @param raidus
	 *            半径（以米为单位）
	 * @return
	 */
	protected double[] getAround(double lon, double lat, int raidus) {

		Double latitude = lat;
		Double longitude = lon;

		double raidusMile = raidus;

		Double dpmLat = 1 / DEGREE;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = DEGREE * Math.cos(latitude * PI_DIV);
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		return new double[] { minLat, minLng, maxLat, maxLng };
	}

	@Override
	public List<UserPosition> getAroundUser(Long userId) throws ManagerException {
		WaterQ waterQ = waterQMapper.getByUserId(userId);
		double[] roundParams = this.getAround(Double.valueOf(waterQ.getLongi()), Double.valueOf(waterQ.getLati()),
				100000);
		List<WaterQ> arounds = waterQMapper.getArounds(userId, roundParams[3], roundParams[1], roundParams[2],
				roundParams[0]);
		if (arounds == null || arounds.isEmpty()) {
			return null;
		}
		List<UserPosition> positionList = new ArrayList<UserPosition>();
		UserPosition position = null;
		for (WaterQ item : arounds) {
			position = new UserPosition();
			position.setLocation(item.getLocation());
			position.setUserId(item.getUserId());
			position.setZongheTds(waterQ.getZongheWaterQ());
			position.setDistance(getDistance(Double.valueOf(waterQ.getLongi()), Double.valueOf(waterQ.getLati()),
					Double.valueOf(item.getLongi()), Double.valueOf(item.getLati())));
			positionList.add(position);
		}
		Collections.sort(positionList);
		List<Long> userIds = new ArrayList<Long>();
		int i = 0;
		List<UserPosition> resultList = new ArrayList<UserPosition>();
		for (UserPosition item : positionList) {
			i++;
			resultList.add(item);
			userIds.add(item.getUserId());
			if (i == 100) {
				break;
			}
		}
		List<WeixinThirdUser> thirdUsers = weixinThirdUserMapper.getByUserIds(userIds);
		Map<Long, WeixinThirdUser> tMap = new HashMap<Long, WeixinThirdUser>();
		for (WeixinThirdUser third : thirdUsers) {
			tMap.put(third.getUserId(), third);
		}
		for (UserPosition up : resultList) {
			up.setHeadImg(tMap.get(up.getUserId()).getHeadimgurl());
		}
		return resultList;
	}

	@Override
	public MyWaterQSimple getMyWaterQSimple(Long userId) throws ManagerException {
		WaterQ waterQ = waterQMapper.getByUserId(userId);
		if (waterQ == null) {
			return null;
		}
		MyWaterQSimple myWaterQ = new MyWaterQSimple();
		myWaterQ.setLoca(waterQ.getLocation());
		myWaterQ.setWaterQ(waterQ.getZongheWaterQ());
		return myWaterQ;
	}

	@Override
	public PkResult pk(Long userId, Long pkUserId) throws ManagerException {
		WaterQ myWaterQ = waterQMapper.getByUserId(userId);
		WaterQ otherWaterQ = waterQMapper.getByUserId(pkUserId);
		WeixinThirdUser thirdUser = weixinThirdUserMapper.getByUserId(pkUserId);
		if (otherWaterQ == null || thirdUser == null) {
			throw new ManagerException("pk用户不存在");
		}
		PkResult pkResult = new PkResult();
		pkResult.setMyHeadImg(CookieUtil.getAvatarFromCookie());
		pkResult.setMyZongheTds(myWaterQ.getZongheWaterQ());
		pkResult.setMyErciLv(myWaterQ.getZongheWaterQ() / 10);
		pkResult.setMyJiegouLv(myWaterQ.getZongheWaterQ() / 15);
		pkResult.setMyXijunLv(myWaterQ.getZongheWaterQ() / 20);

		pkResult.setOtherHeadImg(thirdUser.getHeadimgurl());
		pkResult.setOtherZongheTds(otherWaterQ.getZongheWaterQ());
		pkResult.setOtherErciLv(otherWaterQ.getZongheWaterQ() / 10);
		pkResult.setOtherJiegouLv(otherWaterQ.getZongheWaterQ() / 15);
		pkResult.setOtherXijunLv(otherWaterQ.getZongheWaterQ() / 20);

		if (pkResult.getMyZongheTds() < pkResult.getOtherZongheTds()) {
			pkResult.setPkRet("胜");
		} else if (pkResult.getMyZongheTds() > pkResult.getOtherZongheTds()) {
			pkResult.setPkRet("败");
		} else {
			pkResult.setPkRet("平");
		}
		return pkResult;
	}

	@Override
	public boolean finishedShare(Long userId, String signature) throws ManagerException {

		String val = ehCacheManager.getFromCache(CacheType.HOUR1, signature);
		if (val == null) {
			return false;
		}
		Xunbao xunbao = xunbaoMapper.getByUserId(userId);
		Integer ret = null;
		if (xunbao == null) {
			xunbao = new Xunbao();
			xunbao.setCurrTimes(MAX_TOTAL_TIMES);
			xunbao.setLastModifyDate(new Date());
			xunbao.setPart1(0);
			xunbao.setPart2(0);
			xunbao.setPart3(0);
			xunbao.setPart4(0);
			xunbao.setPart5(0);
			xunbao.setShuidi(0);
			xunbao.setTodayTotalTimes(MAX_TOTAL_TIMES);
			xunbao.setUserId(userId);
			ret = xunbaoMapper.insert(xunbao);
		} else {
			Integer todayTotalTimes = xunbao.getTodayTotalTimes();
			Integer currTimes = xunbao.getCurrTimes();
			Date lastModTime = xunbao.getLastModifyDate();
			lastModTime = DateUtils.truncate(lastModTime, Calendar.DATE);
			if (lastModTime.before(DateUtils.truncate(new Date(), Calendar.DATE))) {
				todayTotalTimes = INIT_TOTAL_TIMES;
				currTimes = INIT_TOTAL_TIMES;
			}
			if (todayTotalTimes < MAX_TOTAL_TIMES) {
				todayTotalTimes++;
				currTimes++;
			}
			ret = xunbaoMapper.updateCurr(userId, currTimes, todayTotalTimes);
		}
		return (ret == null || ret != 1) ? false : true;
	}
}
