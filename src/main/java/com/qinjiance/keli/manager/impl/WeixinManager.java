/**
 * 
 */
package com.qinjiance.keli.manager.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import module.laohu.commons.util.HttpClientUtil;
import module.laohu.commons.util.JsonUtils;
import module.laohu.commons.util.SpringUtils;
import module.laohu.commons.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qinjiance.keli.constants.Constants;
import com.qinjiance.keli.manager.IEhCacheManager;
import com.qinjiance.keli.manager.ISequenceManager;
import com.qinjiance.keli.manager.IWeixinManager;
import com.qinjiance.keli.manager.exception.ManagerException;
import com.qinjiance.keli.manager.impl.EhCacheManager.CacheType;
import com.qinjiance.keli.mapper.UserMapper;
import com.qinjiance.keli.mapper.WeixinThirdUserMapper;
import com.qinjiance.keli.model.po.User;
import com.qinjiance.keli.model.po.WeixinThirdUser;
import com.qinjiance.keli.model.vo.WeixinPublicAccessToken;
import com.qinjiance.keli.model.vo.WeixinPublicUser;
import com.qinjiance.keli.util.CookieUtil;
import com.qinjiance.keli.util.RandomUtil;

/**
 * @author Administrator
 * 
 * @datetime 2016年1月25日 上午1:07:21
 * 
 * @desc
 */
@Service
public class WeixinManager implements IWeixinManager {

	protected final static Logger logger = LoggerFactory.getLogger(WeixinManager.class);

	@Value(value = "#{configProperties['weixin_public_app_id']}")
	private final static String APP_ID = "";
	@Value(value = "#{configProperties['weixin_public_app_secret']}")
	private final static String APP_SECRET = "";
	private final static String PUBLIC_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	private final static String PUBLIC_OAUTH_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
	private final static String PUBLIC_OAUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

	@Value(value = "#{configProperties['weixin_oauth_redirect_url']}")
	private String WEIXIN_OAUTH_REDIRECT_URL = "";

	@Autowired
	private IEhCacheManager ehCacheManager;
	@Autowired
	private WeixinThirdUserMapper weixinThirdUserMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ISequenceManager sequenceManager;

	/**
	 * 
	 */
	public WeixinManager() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qinjiance.keli.manager.IWeixinManager#getWeixinPublicOAuthUrl()
	 */
	@Override
	public String getWeixinPublicOAuthUrl(String location) throws ManagerException {

		String ticket = RandomUtil.getUUID(2);
		ehCacheManager.putToCache(CacheType.MIN15, ticket, location);

		StringBuilder sb = new StringBuilder();
		try {
			sb.append(PUBLIC_OAUTH_URL).append("?appid=").append(APP_ID).append("&redirect_uri=")
					.append(URLEncoder.encode(WEIXIN_OAUTH_REDIRECT_URL, Constants.CHARSET))
					.append("&response_type=code&scope=snsapi_userinfo&state=").append(ticket)
					.append("#wechat_redirect");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException: ", e);
		}
		return sb.toString();
	}

	@Override
	public String weixinPublicOAuthLogin(String code, String state, HttpServletResponse httpServletResponse)
			throws ManagerException {
		String location = ehCacheManager.getFromCache(CacheType.MIN15, state);
		if (StringUtils.isBlank(location)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(PUBLIC_OAUTH_ACCESS_TOKEN_URL).append("?appid=").append(APP_ID).append("&secret=").append(APP_SECRET)
				.append("&code=").append(code).append("&grant_type=authorization_code");
		String response = null;
		try {
			response = HttpClientUtil.invokeGet(sb.toString(), new HashMap<String, String>(), Constants.CHARSET,
					Constants.HTTP_TIMEOUT, Constants.SO_TIMEOUT);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			throw new ManagerException("网络错误，请稍后再试");
		}
		if (StringUtils.isBlank(response)) {
			throw new ManagerException("微信授权错误，请稍后再试");
		}
		WeixinPublicAccessToken accessToken = JsonUtils.parseToObject(response, WeixinPublicAccessToken.class);
		if (accessToken == null || StringUtils.isBlank(accessToken.getOpenid())) {
			throw new ManagerException("微信授权失败，请稍后再试");
		}

		StringBuilder sb2 = new StringBuilder();
		sb2.append(PUBLIC_OAUTH_USERINFO_URL).append("?access_token=").append(accessToken.getAccess_token())
				.append("&openid=").append(accessToken.getOpenid()).append("&lang=zh_CN");
		String response2 = null;
		try {
			response2 = HttpClientUtil.invokeGet(sb2.toString(), new HashMap<String, String>(), Constants.CHARSET,
					Constants.HTTP_TIMEOUT, Constants.SO_TIMEOUT);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			throw new ManagerException("网络错误，请稍后再试");
		}
		if (StringUtils.isBlank(response2)) {
			throw new ManagerException("微信获取用户信息错误，请稍后再试");
		}
		WeixinPublicUser publicUser = JsonUtils.parseToObject(response2, WeixinPublicUser.class);
		if (publicUser == null || StringUtils.isBlank(publicUser.getOpenid())) {
			throw new ManagerException("微信获取用户信息失败，请稍后再试");
		}

		WeixinThirdUser weixinThirdUser = weixinThirdUserMapper.getByOpenid(accessToken.getOpenid());
		User user = null;
		if (weixinThirdUser == null) {
			user = new User();
			Long userId = sequenceManager.getUserIdSeq();
			user.setId(userId);
			user.setName("Clean" + userId);
			user.setSalt("");
			user.setPassword("");
			user.setIp(WebUtils.getIpAddress(SpringUtils.getHttpServletRequest()));
			Integer ret = userMapper.insert(user);
			if (ret == null || ret != 1) {
				return null;
			}

			weixinThirdUser = new WeixinThirdUser();
			weixinThirdUser.setAccessToken(accessToken.getAccess_token());
			weixinThirdUser.setCity(publicUser.getCity());
			weixinThirdUser.setCountry(publicUser.getCountry());
			weixinThirdUser.setExpiresIn(accessToken.getExpires_in());
			weixinThirdUser.setHeadimgurl(publicUser.getHeadimgurl());
			weixinThirdUser.setNickname(publicUser.getNickname());
			weixinThirdUser.setOpenid(accessToken.getOpenid());
			weixinThirdUser.setPrivilege("");
			weixinThirdUser.setProvince(publicUser.getProvince());
			weixinThirdUser.setRefreshToken(accessToken.getRefresh_token());
			weixinThirdUser.setScope(accessToken.getScope());
			weixinThirdUser.setSex(publicUser.getSex());
			weixinThirdUser.setUnionid(accessToken.getUnionid());
			weixinThirdUser.setUserId(userId);
			ret = weixinThirdUserMapper.insert(weixinThirdUser);
			if (ret == null || ret != 1) {
				return null;
			}
		} else {
			user = userMapper.getById(weixinThirdUser.getUserId());
		}
		if (user == null) {
			return null;
		}
		// 种cookie
		boolean result = CookieUtil.setLoginCookie(httpServletResponse, user.getId(), user.getName(),
				weixinThirdUser.getNickname(), weixinThirdUser.getHeadimgurl(), CookieUtil.COOKIE_LIVE_10_DAYS);
		if (!result) {
			return null;
		}
		return location;
	}
}
