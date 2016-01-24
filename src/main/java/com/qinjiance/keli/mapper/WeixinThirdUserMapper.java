/**
 * 
 */
package com.qinjiance.keli.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.qinjiance.keli.model.po.WeixinThirdUser;

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
public interface WeixinThirdUserMapper {

	public Integer insert(WeixinThirdUser weixinThirdUser);

	@Select("select * from weixin_third_user where openid = #{openid}")
	public WeixinThirdUser getByOpenid(@Param("openid") String openid);
}
