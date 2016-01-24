/**
 * 
 */
package com.qinjiance.keli.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.qinjiance.keli.model.po.User;

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
public interface UserMapper {

	public Integer insert(User user);

	@Select("select * from user where id = #{id}")
	public User getById(@Param("id") Long id);
}
