/**
 * 
 */
package com.qinjiance.keli.model.vo;

import java.util.List;

import module.laohu.commons.model.BaseObject;

/**
 * @author Administrator
 *
 * @datetime 2016年2月18日 上午12:49:20
 *
 * @desc
 */
public class WeixinJsConfig extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6837071581239006339L;

	private String appid;
	private Long timestamp;
	private String nonceStr;
	private String signature;
	private List<String> jsApiList;

	/**
	 * 
	 */
	public WeixinJsConfig() {
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public List<String> getJsApiList() {
		return jsApiList;
	}

	public void setJsApiList(List<String> jsApiList) {
		this.jsApiList = jsApiList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
