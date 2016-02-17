/**
 * 
 */
package com.qinjiance.keli.manager;

import java.util.List;

import com.qinjiance.keli.manager.exception.ManagerException;
import com.qinjiance.keli.model.po.Community;
import com.qinjiance.keli.model.vo.MyWaterQ;
import com.qinjiance.keli.model.vo.MyWaterQSimple;
import com.qinjiance.keli.model.vo.PkResult;
import com.qinjiance.keli.model.vo.UserPosition;
import com.qinjiance.keli.model.vo.WaterMap;
import com.qinjiance.keli.model.vo.WaterQPos;
import com.qinjiance.keli.model.vo.XunbaoResult;

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

	public WaterMap waterMap(Long userId) throws ManagerException;

	public XunbaoResult xunbao(Long userId) throws ManagerException;

	public List<UserPosition> getAroundUser(Long userId) throws ManagerException;

	public MyWaterQSimple getMyWaterQSimple(Long userId) throws ManagerException;

	public PkResult pk(Long userId, Long pkUserId) throws ManagerException;

	public boolean finishedShare(Long userId, String signature) throws ManagerException;
}
