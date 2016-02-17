/**
 * 
 */
package com.qinjiance.keli.model.po;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import module.laohu.commons.model.BaseObject;

/**
 * @author Administrator
 *
 * @datetime 2016年2月18日 上午12:12:57
 *
 * @desc
 */
@Alias("weixinAccessToken")
public class WeixinAccessToken extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3594377509028647716L;

	private Long id;
	private String appid;
	private String accessToken;
	private Integer accessTokenExpiresIn;
	private Date accessTokenUpdateTime;
	private String jsapiTicket;
	private Integer jsapiTicketExpiresIn;
	private Date jsapiTicketUpdateTime;
	private Date createTime;
	private Date updateTime;

	/**
	 * 
	 */
	public WeixinAccessToken() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getAccessTokenExpiresIn() {
		return accessTokenExpiresIn;
	}

	public void setAccessTokenExpiresIn(Integer accessTokenExpiresIn) {
		this.accessTokenExpiresIn = accessTokenExpiresIn;
	}

	public Date getAccessTokenUpdateTime() {
		return accessTokenUpdateTime;
	}

	public void setAccessTokenUpdateTime(Date accessTokenUpdateTime) {
		this.accessTokenUpdateTime = accessTokenUpdateTime;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	public Integer getJsapiTicketExpiresIn() {
		return jsapiTicketExpiresIn;
	}

	public void setJsapiTicketExpiresIn(Integer jsapiTicketExpiresIn) {
		this.jsapiTicketExpiresIn = jsapiTicketExpiresIn;
	}

	public Date getJsapiTicketUpdateTime() {
		return jsapiTicketUpdateTime;
	}

	public void setJsapiTicketUpdateTime(Date jsapiTicketUpdateTime) {
		this.jsapiTicketUpdateTime = jsapiTicketUpdateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
