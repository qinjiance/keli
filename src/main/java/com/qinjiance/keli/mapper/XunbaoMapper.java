/**
 * 
 */
package com.qinjiance.keli.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.qinjiance.keli.model.po.Xunbao;

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
public interface XunbaoMapper {

	public Integer insert(Xunbao xunbao);

	@Update("update xunbao set shuidi = shuidi+1, curr_times = curr_times-1, last_get_date = now(), last_modify_date = now() where user_id = #{userId}")
	public Integer updateShuidi(@Param("userId") Long userId);

	@Update("update xunbao set part1 = part1+1, curr_times = curr_times-1, last_get_date = now(), last_modify_date = now() where user_id = #{userId}")
	public Integer updatePart1(@Param("userId") Long userId);

	@Update("update xunbao set part2 = part2+1, curr_times = curr_times-1, last_get_date = now(), last_modify_date = now() where user_id = #{userId}")
	public Integer updatePart2(@Param("userId") Long userId);

	@Update("update xunbao set part3 = part3+1, curr_times = curr_times-1, last_get_date = now(), last_modify_date = now() where user_id = #{userId}")
	public Integer updatePart3(@Param("userId") Long userId);

	@Update("update xunbao set part4 = part4+1, curr_times = curr_times-1, last_get_date = now(), last_modify_date = now() where user_id = #{userId}")
	public Integer updatePart4(@Param("userId") Long userId);

	@Update("update xunbao set part5 = part5+1, curr_times = curr_times-1, last_get_date = now(), last_modify_date = now() where user_id = #{userId}")
	public Integer updatePart5(@Param("userId") Long userId);

	@Update("update xunbao set curr_times = curr_times-1, last_modify_date = now() where user_id = #{userId}")
	public Integer updateCurr(@Param("userId") Long userId);

	@Select("select * from xunbao where user_id = #{userId}")
	public Xunbao getByUserId(@Param("userId") Long userId);
}
