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
 * @datetime 2016年2月16日 上午12:22:08
 *
 * @desc
 */
@Alias("xunbao")
public class Xunbao extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7912290250577387646L;

	private Long id;
	private Long userId;
	private Integer currTimes;
	private Integer todayTotalTimes;
	private Date lastModifyDate;
	private Date lastGetDate;
	private Integer shuidi;
	private Integer part1;
	private Integer part2;
	private Integer part3;
	private Integer part4;
	private Integer part5;
	private Date createTime;
	private Date updateTime;

	/**
	 * 
	 */
	public Xunbao() {
	}

	public Date getLastGetDate() {
		return lastGetDate;
	}

	public void setLastGetDate(Date lastGetDate) {
		this.lastGetDate = lastGetDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTodayTotalTimes() {
		return todayTotalTimes;
	}

	public void setTodayTotalTimes(Integer todayTotalTimes) {
		this.todayTotalTimes = todayTotalTimes;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCurrTimes() {
		return currTimes;
	}

	public void setCurrTimes(Integer currTimes) {
		this.currTimes = currTimes;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Integer getShuidi() {
		return shuidi;
	}

	public void setShuidi(Integer shuidi) {
		this.shuidi = shuidi;
	}

	public Integer getPart1() {
		return part1;
	}

	public void setPart1(Integer part1) {
		this.part1 = part1;
	}

	public Integer getPart2() {
		return part2;
	}

	public void setPart2(Integer part2) {
		this.part2 = part2;
	}

	public Integer getPart3() {
		return part3;
	}

	public void setPart3(Integer part3) {
		this.part3 = part3;
	}

	public Integer getPart4() {
		return part4;
	}

	public void setPart4(Integer part4) {
		this.part4 = part4;
	}

	public Integer getPart5() {
		return part5;
	}

	public void setPart5(Integer part5) {
		this.part5 = part5;
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
