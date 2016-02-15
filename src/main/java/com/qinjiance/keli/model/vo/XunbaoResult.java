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
public class XunbaoResult extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1760447018572601058L;

	private Integer currTimes;
	private Integer todayTotalTimes;
	private Integer shuidi;
	private Integer currParts;
	private Integer totalParts;
	private String libaoName;
	private Integer libaoType;

	/**
	 * 
	 */
	public XunbaoResult() {
	}

	public String getLibaoName() {
		return libaoName;
	}

	public void setLibaoName(String libaoName) {
		this.libaoName = libaoName;
	}

	public Integer getLibaoType() {
		return libaoType;
	}

	public void setLibaoType(Integer libaoType) {
		this.libaoType = libaoType;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
