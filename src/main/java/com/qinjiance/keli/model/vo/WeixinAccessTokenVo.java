/**
 * 
 */
package com.qinjiance.keli.model.vo;

import module.laohu.commons.model.BaseObject;

/**
 * @author Administrator
 *
 * @datetime 2016年1月25日 上午1:51:25
 *
 * @desc
 */
public class WeixinAccessTokenVo extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7012756109678732938L;

	private String access_token;
	private Integer expires_in;

	/**
	 * 
	 */
	public WeixinAccessTokenVo() {
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
