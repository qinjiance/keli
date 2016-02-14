/**
 * 
 */
package com.qinjiance.keli.model.po;

import org.springframework.data.annotation.Id;

import module.laohu.commons.model.BaseObject;

/**
 * @author Administrator
 *
 * @datetime 2016年2月10日 下午11:59:14
 *
 * @desc
 */
public class Community extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -945866150241151517L;

	@Id
	private String id;
	private String province;
	private String city;
	private String area;
	private String estate;
	private Long lbsIs;
	private Integer tds;
	private String lng;
	private String lat;

	/**
	 * 
	 */
	public Community() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getEstate() {
		return estate;
	}

	public void setEstate(String estate) {
		this.estate = estate;
	}

	public Long getLbsIs() {
		return lbsIs;
	}

	public void setLbsIs(Long lbsIs) {
		this.lbsIs = lbsIs;
	}

	public Integer getTds() {
		return tds;
	}

	public void setTds(Integer tds) {
		this.tds = tds;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
