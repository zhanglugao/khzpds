package com.khzpds.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.khzpds.vo.MenuInfo;

@SuppressWarnings("serial")
public class SessionInfo implements Serializable{
    private String userId;
    private String userName;
    private String realName;
    private String mail;
    private String sendMail;
    private String password;
    private String verifyCode;//注册时发送的验证码
    private Date sendVerifyCodeTime;
    private Boolean ifLogin;//是否登录
    private List<MenuInfo> menus;
    
	public Boolean getIfLogin() {
		return ifLogin;
	}
	public void setIfLogin(Boolean ifLogin) {
		this.ifLogin = ifLogin;
	}
	public String getUserId() {
		return userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public List<MenuInfo> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuInfo> menus) {
		this.menus = menus;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getSendMail() {
		return sendMail;
	}
	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}
	public Date getSendVerifyCodeTime() {
		return sendVerifyCodeTime;
	}
	public void setSendVerifyCodeTime(Date sendVerifyCodeTime) {
		this.sendVerifyCodeTime = sendVerifyCodeTime;
	}
    
}
