/**
 * 
 */
package com.qinjiance.keli.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.qinjiance.keli.model.po.WaterQ;

/**
 * @author "Jiance Qin"
 * 
 * @date 2016年1月14日
 * 
 * @time 下午6:24:13
 * 
 * @desc
 * 
 */
public interface WaterQMapper {

	public Integer insert(WaterQ waterQ);

	@Select("select * from water_q where user_id = #{userId}")
	public WaterQ getByUserId(@Param("userId") Long userId);
}
