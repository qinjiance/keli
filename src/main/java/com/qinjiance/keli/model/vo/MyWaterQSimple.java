/**
 * 
 */
package com.qinjiance.keli.model.vo;

import module.laohu.commons.model.BaseObject;

/**
 * @author "Jiance Qin"
 * 
 * @date 2016年1月26日
 * 
 * @time 上午10:41:16
 * 
 * @desc
 * 
 */
public class MyWaterQSimple extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4590878138930810472L;

	private String loca;
	private Integer waterQ;
	private String city;

	/**
	 * 
	 */
	public MyWaterQSimple() {

	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLoca() {
		return loca;
	}

	public void setLoca(String loca) {
		this.loca = loca;
	}

	public Integer getWaterQ() {
		return waterQ;
	}

	public void setWaterQ(Integer waterQ) {
		this.waterQ = waterQ;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
