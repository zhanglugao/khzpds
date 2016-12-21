
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class UserInfoInfo implements Serializable
{
	
	private String id;   
	private String userName;   
	private String mail;   
	private String password;   
	private java.util.Date createTime;   
	private String realName;   
	private String status;   
	private String sex;   
    
    public UserInfoInfo(){}
   
    
    /**
     * 
     */ 	
	public String getId(){
        return id;
    }
    
     /**
     * 
     */ 	
    public void setId(String id){
        this.id=id;
    }
    
    /**
     * 会员名称
     */ 	
	public String getUserName(){
        return userName;
    }
    
     /**
     * 会员名称
     */ 	
    public void setUserName(String userName){
        this.userName=userName;
    }
    
    /**
     * 邮箱地址
     */ 	
	public String getMail(){
        return mail;
    }
    
     /**
     * 邮箱地址
     */ 	
    public void setMail(String mail){
        this.mail=mail;
    }
    
    /**
     * 密码
     */ 	
	public String getPassword(){
        return password;
    }
    
     /**
     * 密码
     */ 	
    public void setPassword(String password){
        this.password=password;
    }
    
    /**
     * 
     */ 	
	public java.util.Date getCreateTime(){
        return createTime;
    }
    
     /**
     * 
     */ 	
    public void setCreateTime(java.util.Date createTime){
        this.createTime=createTime;
    }
    
    /**
     * 
     */ 	
	public String getRealName(){
        return realName;
    }
    
     /**
     * 
     */ 	
    public void setRealName(String realName){
        this.realName=realName;
    }
    
    /**
     * 
     */ 	
	public String getStatus(){
        return status;
    }
    
     /**
     * 
     */ 	
    public void setStatus(String status){
        this.status=status;
    }
    
    /**
     * 
     */ 	
	public String getSex(){
        return sex;
    }
    
     /**
     * 
     */ 	
    public void setSex(String sex){
        this.sex=sex;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("UserName",getUserName())
           .append("Mail",getMail())
           .append("Password",getPassword())
           .append("CreateTime",getCreateTime())
           .append("RealName",getRealName())
           .append("Status",getStatus())
           .append("Sex",getSex())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserInfoInfo == false) return false;
		if(this == obj) return true;
		UserInfoInfo other = (UserInfoInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***///
	private String roleIds;
	private String roleNames;
	
	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	//--CustomEnd*****///
}

