/**
 * 
 */
package com.qinjiance.keli.model.vo;

import module.laohu.commons.model.BaseObject;

/**
 * @author Administrator
 *
 * @datetime 2016年2月16日 下午11:19:27
 *
 * @desc
 */
public class UserPosition extends BaseObject implements Comparable<UserPosition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3796614896872424641L;

	private Long userId;
	private String location;
	private Integer distance;
	private String headImg;
	private Integer zongheTds;

	/**
	 * 
	 */
	public UserPosition() {
	}

	public Integer getZongheTds() {
		return zongheTds;
	}

	public void setZongheTds(Integer zongheTds) {
		this.zongheTds = zongheTds;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int compareTo(UserPosition o) {
		if (this.distance > o.getDistance()) {
			return 1;
		} else if (this.distance < o.getDistance()) {
			return -1;
		} else {
			return 0;
		}
	}

}
