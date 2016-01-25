/**
 * 
 */
package com.qinjiance.keli.manager;

import com.qinjiance.keli.manager.exception.ManagerException;
import com.qinjiance.keli.model.vo.WaterQPos;

/**
 * @author "Jiance Qin"
 * 
 * @date 2016年1月25日
 * 
 * @time 下午2:56:27
 * 
 * @desc
 * 
 */
public interface IWaterManager {

	public WaterQPos getWaterQ(String lati, String longi, Integer yinshui, Integer tongzhuangshui, Integer baojie)
			throws ManagerException;
}
