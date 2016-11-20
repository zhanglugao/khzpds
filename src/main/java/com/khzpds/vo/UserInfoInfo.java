package com.khzpds.vo;
import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@SuppressWarnings("serial")
public class UserInfoInfo implements Serializable
{
	
	private String id;   
	private String userName;   
	private Integer mail;   
	private String password;   
    
    public UserInfoInfo(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getMail() {
		return mail;
	}

	public void setMail(Integer mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
   
    

    
    //--CustomBegin***/////--CustomEnd*****///
}