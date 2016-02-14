/**
 * 
 */
package com.qinjiance.keli.model.po;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import module.laohu.commons.model.BaseObject;

/**
 * @author "Jiance Qin"
 * 
 * @date 2016年1月26日
 * 
 * @time 上午10:09:45
 * 
 * @desc
 * 
 */
@Alias("waterQ")
public class WaterQ extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4348669819837216439L;

	private Long id;
	private String location;
	private Long userId;
	private String lati;
	private String longi;
	private Integer yinshui;
	private Integer tongzhuangshui;
	private Integer yinshuiji;
	private String modelId;
	private Integer modelWaterQ;
	private String modelLoca;
	private String modelLati;
	private String modelLongi;
	private Integer localWaterQ;
	private Integer yinshuiWaterQ;
	private Integer tongWaterQ;
	private Integer yinshuijiWaterQ;
	private Integer zongheWaterQ;
	private Date createTime;
	private Date updateTime;

	/**
	 * 
	 */
	public WaterQ() {
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the lati
	 */
	public String getLati() {
		return lati;
	}

	/**
	 * @param lati
	 *            the lati to set
	 */
	public void setLati(String lati) {
		this.lati = lati;
	}

	/**
	 * @return the longi
	 */
	public String getLongi() {
		return longi;
	}

	/**
	 * @param longi
	 *            the longi to set
	 */
	public void setLongi(String longi) {
		this.longi = longi;
	}

	/**
	 * @return the yinshui
	 */
	public Integer getYinshui() {
		return yinshui;
	}

	/**
	 * @param yinshui
	 *            the yinshui to set
	 */
	public void setYinshui(Integer yinshui) {
		this.yinshui = yinshui;
	}

	/**
	 * @return the tongzhuangshui
	 */
	public Integer getTongzhuangshui() {
		return tongzhuangshui;
	}

	/**
	 * @param tongzhuangshui
	 *            the tongzhuangshui to set
	 */
	public void setTongzhuangshui(Integer tongzhuangshui) {
		this.tongzhuangshui = tongzhuangshui;
	}

	/**
	 * @return the yinshuiji
	 */
	public Integer getYinshuiji() {
		return yinshuiji;
	}

	/**
	 * @param yinshuiji
	 *            the yinshuiji to set
	 */
	public void setYinshuiji(Integer yinshuiji) {
		this.yinshuiji = yinshuiji;
	}

	/**
	 * @return the modelId
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * @param modelId
	 *            the modelId to set
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/**
	 * @return the modelWaterQ
	 */
	public Integer getModelWaterQ() {
		return modelWaterQ;
	}

	/**
	 * @param modelWaterQ
	 *            the modelWaterQ to set
	 */
	public void setModelWaterQ(Integer modelWaterQ) {
		this.modelWaterQ = modelWaterQ;
	}

	/**
	 * @return the modelLoca
	 */
	public String getModelLoca() {
		return modelLoca;
	}

	/**
	 * @param modelLoca
	 *            the modelLoca to set
	 */
	public void setModelLoca(String modelLoca) {
		this.modelLoca = modelLoca;
	}

	/**
	 * @return the modelLati
	 */
	public String getModelLati() {
		return modelLati;
	}

	/**
	 * @param modelLati
	 *            the modelLati to set
	 */
	public void setModelLati(String modelLati) {
		this.modelLati = modelLati;
	}

	/**
	 * @return the modelLongi
	 */
	public String getModelLongi() {
		return modelLongi;
	}

	/**
	 * @param modelLongi
	 *            the modelLongi to set
	 */
	public void setModelLongi(String modelLongi) {
		this.modelLongi = modelLongi;
	}

	/**
	 * @return the localWaterQ
	 */
	public Integer getLocalWaterQ() {
		return localWaterQ;
	}

	/**
	 * @param localWaterQ
	 *            the localWaterQ to set
	 */
	public void setLocalWaterQ(Integer localWaterQ) {
		this.localWaterQ = localWaterQ;
	}

	/**
	 * @return the yinshuiWaterQ
	 */
	public Integer getYinshuiWaterQ() {
		return yinshuiWaterQ;
	}

	/**
	 * @param yinshuiWaterQ
	 *            the yinshuiWaterQ to set
	 */
	public void setYinshuiWaterQ(Integer yinshuiWaterQ) {
		this.yinshuiWaterQ = yinshuiWaterQ;
	}

	/**
	 * @return the tongWaterQ
	 */
	public Integer getTongWaterQ() {
		return tongWaterQ;
	}

	/**
	 * @param tongWaterQ
	 *            the tongWaterQ to set
	 */
	public void setTongWaterQ(Integer tongWaterQ) {
		this.tongWaterQ = tongWaterQ;
	}

	/**
	 * @return the yinshuijiWaterQ
	 */
	public Integer getYinshuijiWaterQ() {
		return yinshuijiWaterQ;
	}

	/**
	 * @param yinshuijiWaterQ
	 *            the yinshuijiWaterQ to set
	 */
	public void setYinshuijiWaterQ(Integer yinshuijiWaterQ) {
		this.yinshuijiWaterQ = yinshuijiWaterQ;
	}

	/**
	 * @return the zongheWaterQ
	 */
	public Integer getZongheWaterQ() {
		return zongheWaterQ;
	}

	/**
	 * @param zongheWaterQ
	 *            the zongheWaterQ to set
	 */
	public void setZongheWaterQ(Integer zongheWaterQ) {
		this.zongheWaterQ = zongheWaterQ;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
