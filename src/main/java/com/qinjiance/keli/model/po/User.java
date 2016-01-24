/**
 * 
 */
package com.qinjiance.keli.model.po;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import module.laohu.commons.model.BaseObject;

/**
 * @author "Jiance Qin"
 * 
 * @date 2016年1月14日
 * 
 * @time 下午6:24:04
 * 
 * @desc
 * 
 */
@Alias("user")
public class User extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3146715554401793947L;

	private Long id;
	private String name;
	private String salt;
	private String password;
	private String ip;
	private Date createTime;
	private Date updateTime;

	/**
	 * 
	 */
	public User() {

	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
