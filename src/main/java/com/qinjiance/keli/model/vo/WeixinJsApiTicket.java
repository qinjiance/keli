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
public class WeixinJsApiTicket extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7012756109678732938L;

	private Integer errcode;
	private String errmsg;
	private String ticket;
	private Integer expires_in;

	/**
	 * 
	 */
	public WeixinJsApiTicket() {
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
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
