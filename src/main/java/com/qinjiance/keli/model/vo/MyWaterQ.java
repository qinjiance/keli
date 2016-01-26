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
public class MyWaterQ extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4590878138930810472L;

	private String loca;
	private Integer waterQ;
	private String waterQDesc;
	private String localWaterQDesc;
	private String yinshuiWaterQDesc;
	private String tongWaterQDesc;
	private String yinshuijiWaterQDesc;

	/**
	 * 
	 */
	public MyWaterQ() {

	}

	/**
	 * @return the loca
	 */
	public String getLoca() {
		return loca;
	}

	/**
	 * @param loca the loca to set
	 */
	public void setLoca(String loca) {
		this.loca = loca;
	}

	/**
	 * @return the waterQ
	 */
	public Integer getWaterQ() {
		return waterQ;
	}

	/**
	 * @param waterQ
	 *            the waterQ to set
	 */
	public void setWaterQ(Integer waterQ) {
		this.waterQ = waterQ;
	}

	/**
	 * @return the waterQDesc
	 */
	public String getWaterQDesc() {
		return waterQDesc;
	}

	/**
	 * @param waterQDesc
	 *            the waterQDesc to set
	 */
	public void setWaterQDesc(String waterQDesc) {
		this.waterQDesc = waterQDesc;
	}

	/**
	 * @return the localWaterQDesc
	 */
	public String getLocalWaterQDesc() {
		return localWaterQDesc;
	}

	/**
	 * @param localWaterQDesc
	 *            the localWaterQDesc to set
	 */
	public void setLocalWaterQDesc(String localWaterQDesc) {
		this.localWaterQDesc = localWaterQDesc;
	}

	/**
	 * @return the yinshuiWaterQDesc
	 */
	public String getYinshuiWaterQDesc() {
		return yinshuiWaterQDesc;
	}

	/**
	 * @param yinshuiWaterQDesc
	 *            the yinshuiWaterQDesc to set
	 */
	public void setYinshuiWaterQDesc(String yinshuiWaterQDesc) {
		this.yinshuiWaterQDesc = yinshuiWaterQDesc;
	}

	/**
	 * @return the tongWaterQDesc
	 */
	public String getTongWaterQDesc() {
		return tongWaterQDesc;
	}

	/**
	 * @param tongWaterQDesc
	 *            the tongWaterQDesc to set
	 */
	public void setTongWaterQDesc(String tongWaterQDesc) {
		this.tongWaterQDesc = tongWaterQDesc;
	}

	/**
	 * @return the yinshuijiWaterQDesc
	 */
	public String getYinshuijiWaterQDesc() {
		return yinshuijiWaterQDesc;
	}

	/**
	 * @param yinshuijiWaterQDesc
	 *            the yinshuijiWaterQDesc to set
	 */
	public void setYinshuijiWaterQDesc(String yinshuijiWaterQDesc) {
		this.yinshuijiWaterQDesc = yinshuijiWaterQDesc;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
