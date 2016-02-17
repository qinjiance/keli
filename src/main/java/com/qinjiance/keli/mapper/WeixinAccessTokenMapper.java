/**
 * 
 */
package com.qinjiance.keli.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.qinjiance.keli.model.po.WeixinAccessToken;

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
public interface WeixinAccessTokenMapper {

	public Integer insert(WeixinAccessToken weixinAccessToken);

	@Select("select * from weixin_access_token where appid = #{appid}")
	public WeixinAccessToken getByAppid(@Param("appid") String appid);

	@Update("update weixin_access_token set access_token=#{accessToken},access_token_expires_in=#{accessTokenExpiresIn},access_token_update_time=#{accessTokenUpdateTime},jsapi_ticket=#{jsapiTicket},jsapi_ticket_expires_in=#{jsapiTicketExpiresIn},jsapi_ticket_update_time=#{jsapiTicketUpdateTime} where appid=#{appid}")
	public Integer update(@Param("appid") String appid, @Param("accessToken") String accessToken,
			@Param("accessTokenExpiresIn") Integer accessTokenExpiresIn,
			@Param("accessTokenUpdateTime") Date accessTokenUpdateTime, @Param("jsapiTicket") String jsapiTicket,
			@Param("jsapiTicketExpiresIn") Integer jsapiTicketExpiresIn,
			@Param("jsapiTicketUpdateTime") Date jsapiTicketUpdateTime);

	@Update("update weixin_access_token set jsapi_ticket=#{jsapiTicket},jsapi_ticket_expires_in=#{jsapiTicketExpiresIn},jsapi_ticket_update_time=#{jsapiTicketUpdateTime} where appid=#{appid}")
	public Integer updateTicket(@Param("appid") String appid, @Param("jsapiTicket") String jsapiTicket,
			@Param("jsapiTicketExpiresIn") Integer jsapiTicketExpiresIn,
			@Param("jsapiTicketUpdateTime") Date jsapiTicketUpdateTime);
}
