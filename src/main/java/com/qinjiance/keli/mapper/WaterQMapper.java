/**
 * 
 */
package com.qinjiance.keli.mapper;

import java.util.List;

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

	@Select("select * from water_q where user_id <> #{userId} and CONVERT(longi,DECIMAL) > #{minLng} and CONVERT(longi,DECIMAL) < #{maxLng} and CONVERT(lati,DECIMAL) > #{minLat} and CONVERT(lati,DECIMAL) < #{maxLat}")
	public List<WaterQ> getArounds(@Param("userId") Long userId, @Param("maxLng") Double maxLng,
			@Param("minLng") Double minLng, @Param("maxLat") Double maxLat, @Param("minLat") Double minLat);
}
