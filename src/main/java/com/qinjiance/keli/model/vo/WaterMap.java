/**
 * 
 */
package com.qinjiance.keli.model.vo;

import module.laohu.commons.model.BaseObject;

/**
 * @author Administrator
 *
 * @datetime 2016年2月15日 上午2:25:09
 *
 * @desc
 */
public class WaterMap extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1760447018572601058L;

	private String lng;
	private String lat;
	private Integer jieGouLv;
	private Integer erCiLv;
	private Integer xiJunLv;
	private Integer currTimes;
	private Integer todayTotalTimes;
	private Integer shuidi;
	private Integer currParts;
	private Integer totalParts;

	/**
	 * 
	 */
	public WaterMap() {
	}

	public Integer getCurrTimes() {
		return currTimes;
	}

	public void setCurrTimes(Integer currTimes) {
		this.currTimes = currTimes;
	}

	public Integer getTodayTotalTimes() {
		return todayTotalTimes;
	}

	public void setTodayTotalTimes(Integer todayTotalTimes) {
		this.todayTotalTimes = todayTotalTimes;
	}

	public Integer getShuidi() {
		return shuidi;
	}

	public void setShuidi(Integer shuidi) {
		this.shuidi = shuidi;
	}

	public Integer getCurrParts() {
		return currParts;
	}

	public void setCurrParts(Integer currParts) {
		this.currParts = currParts;
	}

	public Integer getTotalParts() {
		return totalParts;
	}

	public void setTotalParts(Integer totalParts) {
		this.totalParts = totalParts;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Integer getJieGouLv() {
		return jieGouLv;
	}

	public void setJieGouLv(Integer jieGouLv) {
		this.jieGouLv = jieGouLv;
	}

	public Integer getErCiLv() {
		return erCiLv;
	}

	public void setErCiLv(Integer erCiLv) {
		this.erCiLv = erCiLv;
	}

	public Integer getXiJunLv() {
		return xiJunLv;
	}

	public void setXiJunLv(Integer xiJunLv) {
		this.xiJunLv = xiJunLv;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
