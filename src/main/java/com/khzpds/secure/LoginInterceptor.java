package com.khzpds.secure;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.khzpds.base.BusinessConfig;
import com.khzpds.base.SessionInfo;
import com.khzpds.service.UserInfoService;
import com.khzpds.vo.UserInfoInfo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserInfoService userInfoService;
	private static final Log log = LogFactory.getLog(LoginInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (request.getSession().getAttribute(BusinessConfig.USER_SESSION_KEY) == null ) {
			//从cookie中取数据
			String userName=getCookie("userName", request);
			if(StringUtils.isNotBlank(userName)){
				//userName=new String(Base64.decodeBase64(userName),"utf-8");
				//String password=new String(Base64.decodeBase64(getCookie("password", request)),"utf-8");
				String password=getCookie("password", request);
				UserInfoInfo findInfo=new UserInfoInfo();
				findInfo.setUserName(userName);
				findInfo.setPassword(password);
				List<UserInfoInfo> users=userInfoService.findByParam(findInfo);
				if(users!=null&&users.size()>0){
					//登录
					SessionInfo session=userInfoService.setSession(users.get(0));
					request.getSession().setAttribute(BusinessConfig.USER_SESSION_KEY, session);
					return true;
				}
			}
			return doFalse(ifAjax(request), response,request);
		}else{
			SessionInfo session=(SessionInfo) request.getSession().getAttribute(BusinessConfig.USER_SESSION_KEY);
			if(session.getUserName()==null){
				return doFalse(ifAjax(request), response,request);
			}
		}
		return true;

	}
	private String getCookie(String name,HttpServletRequest request) throws UnsupportedEncodingException{
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		if(cookies==null||cookies.length==0){
			return null;
		}
		for(Cookie cookie : cookies){
		    String key=cookie.getName();// get the cookie name
		    if(name.equals(key)){
		    	String value=URLDecoder.decode(URLDecoder.decode(cookie.getValue(), "utf-8"), "utf-8");
		    	return value;
		    }
		}
		return null;
		
	}
	private boolean doFalse(boolean ifAjax, HttpServletResponse response,HttpServletRequest request) throws IOException {
		if (ifAjax) {
			response.getWriter().print("NOSESSION");
		} else {
			response.sendRedirect("/login.html");
		}
		return false;
	}
	/**
	 * 获取当前网络ip
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					log.error(e.getMessage(), e);
				}
				if (inet != null) {

					ipAddress = inet.getHostAddress();
				}
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	/**
	 * 判断是否为ajax请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private boolean ifAjax(HttpServletRequest request) throws IOException {
		String requestType = request.getHeader("X-Requested-With");
		if (StringUtils.isNotBlank(requestType)) {
			return true;
		}
		return false;
	}

}