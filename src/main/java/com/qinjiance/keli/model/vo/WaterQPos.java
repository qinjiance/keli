/**
 * 
 */
package com.qinjiance.keli.model.vo;

import com.qinjiance.keli.model.po.Community;

import module.laohu.commons.model.BaseObject;

/**
 * @author "Jiance Qin"
 * 
 * @date 2016年1月25日
 * 
 * @time 下午2:57:07
 * 
 * @desc
 * 
 */
public class WaterQPos extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8518364968569425160L;

	private Integer waterQ;
	private String degree;
	private String desc;
	private String posName;
	private Community community;

	/**
	 * 
	 */
	public WaterQPos() {

	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the waterQ
	 */
	public Integer getWaterQ() {
		return waterQ;
	}

	/**
	 * @param waterQ
	 *            the waterQ to set
	 */
	public void setWaterQ(Integer waterQ) {
		this.waterQ = waterQ;
	}

	/**
	 * @return the posName
	 */
	public String getPosName() {
		return posName;
	}

	/**
	 * @param posName
	 *            the posName to set
	 */
	public void setPosName(String posName) {
		this.posName = posName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
