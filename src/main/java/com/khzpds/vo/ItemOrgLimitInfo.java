
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class ItemOrgLimitInfo implements Serializable
{
	
	private String id;   
	private String orgId;   
	private String itemId;   
	private String limitNum;   
	private java.util.Date createTime;   
	private String createUser;   
	private java.util.Date updateTime;   
	private String updateUser;   
	private String spare1;   
	private String spare2;   
	private String spare3;   
    
    public ItemOrgLimitInfo(){}
   
    
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
     * 组织机构id
     */ 	
	public String getOrgId(){
        return orgId;
    }
    
     /**
     * 组织机构id
     */ 	
    public void setOrgId(String orgId){
        this.orgId=orgId;
    }
    
    /**
     * 项目id
     */ 	
	public String getItemId(){
        return itemId;
    }
    
     /**
     * 项目id
     */ 	
    public void setItemId(String itemId){
        this.itemId=itemId;
    }
    
    /**
     * 限额值
     */ 	
	public String getLimitNum(){
        return limitNum;
    }
    
     /**
     * 限额值
     */ 	
    public void setLimitNum(String limitNum){
        this.limitNum=limitNum;
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
	public String getCreateUser(){
        return createUser;
    }
    
     /**
     * 
     */ 	
    public void setCreateUser(String createUser){
        this.createUser=createUser;
    }
    
    /**
     * 
     */ 	
	public java.util.Date getUpdateTime(){
        return updateTime;
    }
    
     /**
     * 
     */ 	
    public void setUpdateTime(java.util.Date updateTime){
        this.updateTime=updateTime;
    }
    
    /**
     * 
     */ 	
	public String getUpdateUser(){
        return updateUser;
    }
    
     /**
     * 
     */ 	
    public void setUpdateUser(String updateUser){
        this.updateUser=updateUser;
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
           .append("OrgId",getOrgId())
           .append("ItemId",getItemId())
           .append("LimitNum",getLimitNum())
           .append("CreateTime",getCreateTime())
           .append("CreateUser",getCreateUser())
           .append("UpdateTime",getUpdateTime())
           .append("UpdateUser",getUpdateUser())
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
		if(obj instanceof ItemOrgLimitInfo == false) return false;
		if(this == obj) return true;
		ItemOrgLimitInfo other = (ItemOrgLimitInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

