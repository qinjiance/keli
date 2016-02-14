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
public class WaterPool extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -945866150241151517L;

	@Id
	private String id;
	private String state;
	private String lng;
	private String lat;

	/**
	 * 
	 */
	public WaterPool() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
