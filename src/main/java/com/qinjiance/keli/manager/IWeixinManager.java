/**
 * 
 */
package com.qinjiance.keli.manager;

import javax.servlet.http.HttpServletResponse;

import com.qinjiance.keli.manager.exception.ManagerException;
import com.qinjiance.keli.model.vo.WeixinJsConfig;

/**
 * @author Administrator
 *
 * @datetime 2016年1月25日 上午1:00:05
 *
 * @desc
 */
public interface IWeixinManager {
	public String getWeixinPublicOAuthUrl(String location) throws ManagerException;

	public String weixinPublicOAuthLogin(String code, String state, HttpServletResponse httpServletResponse)
			throws ManagerException;

	public WeixinJsConfig getWeixinJsConfig(String url) throws ManagerException;
}
