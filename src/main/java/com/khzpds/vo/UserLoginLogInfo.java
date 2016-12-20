
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class UserLoginLogInfo implements Serializable
{
	
	private String id;   
	private String userId;   
	private java.util.Date loginTime;   
	private String referSite;   
	private String loginType;   
	private String spare1;   
	private String spare2;   
	private String spare3;   
    
    public UserLoginLogInfo(){}
   
    
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
     * 
     */ 	
	public String getUserId(){
        return userId;
    }
    
     /**
     * 
     */ 	
    public void setUserId(String userId){
        this.userId=userId;
    }
    
    /**
     * 
     */ 	
	public java.util.Date getLoginTime(){
        return loginTime;
    }
    
     /**
     * 
     */ 	
    public void setLoginTime(java.util.Date loginTime){
        this.loginTime=loginTime;
    }
    
    /**
     * 来源网址
     */ 	
	public String getReferSite(){
        return referSite;
    }
    
     /**
     * 来源网址
     */ 	
    public void setReferSite(String referSite){
        this.referSite=referSite;
    }
    
    /**
     * 0 pc 1手机
     */ 	
	public String getLoginType(){
        return loginType;
    }
    
     /**
     * 0 pc 1手机
     */ 	
    public void setLoginType(String loginType){
        this.loginType=loginType;
    }
    
    /**
     * 
     */ 	
	public String getSpare1(){
        return spare1;
    }
    
     /**
     * 
     */ 	
    public void setSpare1(String spare1){
        this.spare1=spare1;
    }
    
    /**
     * 
     */ 	
	public String getSpare2(){
        return spare2;
    }
    
     /**
     * 
     */ 	
    public void setSpare2(String spare2){
        this.spare2=spare2;
    }
    
    /**
     * 
     */ 	
	public String getSpare3(){
        return spare3;
    }
    
     /**
     * 
     */ 	
    public void setSpare3(String spare3){
        this.spare3=spare3;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("UserId",getUserId())
           .append("LoginTime",getLoginTime())
           .append("ReferSite",getReferSite())
           .append("LoginType",getLoginType())
           .append("Spare1",getSpare1())
           .append("Spare2",getSpare2())
           .append("Spare3",getSpare3())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserLoginLogInfo == false) return false;
		if(this == obj) return true;
		UserLoginLogInfo other = (UserLoginLogInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

