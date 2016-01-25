/**
 * 
 */
package com.qinjiance.keli.manager.impl;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import com.qinjiance.keli.manager.IWaterManager;
import com.qinjiance.keli.manager.exception.ManagerException;
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

	/**
	 * 
	 */
	public WaterManager() {
	}

	@Override
	public WaterQPos getWaterQ(String lati, String longi, Integer yinshui) throws ManagerException {
		WaterQPos waterQPos = new WaterQPos();
		waterQPos.setPosName("完美世界大厦");
		waterQPos.setWaterQ(RandomUtils.nextInt(1000));
		waterQPos.setDesc("一般");
		return waterQPos;
	}

}
