/**
 * 
 */
package com.qinjiance.keli.manager.impl;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qinjiance.keli.manager.IWaterManager;
import com.qinjiance.keli.manager.exception.ManagerException;
import com.qinjiance.keli.mapper.WaterQMapper;
import com.qinjiance.keli.model.po.WaterQ;
import com.qinjiance.keli.model.vo.MyWaterQ;
import com.qinjiance.keli.model.vo.WaterQPos;

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

	@Autowired
	private WaterQMapper waterQMapper;

	/**
	 * 
	 */
	public WaterManager() {
	}

	@Override
	public WaterQPos getWaterQ(String lati, String longi, Integer yinshui, Integer tongzhuangshui, Integer baojie)
			throws ManagerException {
		// 监测点tds
		Integer tds = RandomUtils.nextInt(1000);
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
		waterQPos.setPosName("完美世界大厦");
		waterQPos.setWaterQ(tds);
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
			WaterQPos localWaterQ = getWaterQ(lati, longi, null,null,null);
			waterQ.setLocalWaterQ(localWaterQ.getWaterQ());
			waterQ.setLongi(longi);
			waterQ.setModelId(1L);
			waterQ.setModelLati(lati);
			waterQ.setModelLoca(location);
			waterQ.setLocation(location);
			waterQ.setModelLongi(longi);
			waterQ.setModelWaterQ(localWaterQ.getWaterQ());
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
			WaterQPos zongheWaterQ = getWaterQ(lati, longi, yinshui, tongzhuangshui, baojie);
			waterQ.setZongheWaterQ(zongheWaterQ.getWaterQ());
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

}
