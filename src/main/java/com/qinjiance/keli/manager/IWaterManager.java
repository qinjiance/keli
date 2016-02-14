/**
 * 
 */
package com.qinjiance.keli.manager;

import com.qinjiance.keli.manager.exception.ManagerException;
import com.qinjiance.keli.model.po.Community;
import com.qinjiance.keli.model.vo.MyWaterQ;
import com.qinjiance.keli.model.vo.WaterMap;
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

	public MyWaterQ myWaterQ(Long userId, String lati, String longi, String location, Integer yinshui,
			Integer tongzhuangshui, Integer baojie) throws ManagerException;

	public MyWaterQ getMyWaterQ(Long userId) throws ManagerException;

	public Community getCommunity(String lati, String longi) throws ManagerException;

	public String getLbsLocationName(String lati, String longi) throws ManagerException;

	public WaterMap waterMap(Long userIdFromCookie) throws ManagerException;
}
