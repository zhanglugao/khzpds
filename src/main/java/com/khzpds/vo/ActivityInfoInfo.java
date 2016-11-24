
package com.khzpds.vo;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class ActivityInfoInfo implements Serializable
{
	
	private String id;   
	private Integer year;   
	private String name;   
	private String description;   
	private String status;   
	private java.util.Date startTime;   
	private java.util.Date endTime;   
	private java.util.Date createTime;   
	private String createUser;   
	private java.util.Date updateTime;   
	private String updateUser;   
	private String udef1;   
	private String udef2;   
	private String udef3;   
	private String udef4;   
	private String udef5;   
	private String udef6;   
	private String udef7;   
	private String udef8;   
	private String udef9;   
	private String udef10;   
    
    public ActivityInfoInfo(){}
   
    
    /**
     * 主键id
     */ 	
	public String getId(){
        return id;
    }
    
     /**
     * 主键id
     */ 	
    public void setId(String id){
        this.id=id;
    }
    
    /**
     * 活动年度
     */ 	
	public Integer getYear(){
        return year;
    }
    
     /**
     * 活动年度
     */ 	
    public void setYear(Integer year){
        this.year=year;
    }
    
    /**
     * 活动名称
     */ 	
	public String getName(){
        return name;
    }
    
     /**
     * 活动名称
     */ 	
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * 活动介绍
     */ 	
	public String getDescription(){
        return description;
    }
    
     /**
     * 活动介绍
     */ 	
    public void setDescription(String description){
        this.description=description;
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
	public java.util.Date getStartTime(){
        return startTime;
    }
    
     /**
     * 
     */ 	
    public void setStartTime(java.util.Date startTime){
        this.startTime=startTime;
    }
    
    /**
     * 
     */ 	
	public java.util.Date getEndTime(){
        return endTime;
    }
    
     /**
     * 
     */ 	
    public void setEndTime(java.util.Date endTime){
        this.endTime=endTime;
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
	public String getUdef1(){
        return udef1;
    }
    
     /**
     * 
     */ 	
    public void setUdef1(String udef1){
        this.udef1=udef1;
    }
    
    /**
     * 
     */ 	
	public String getUdef2(){
        return udef2;
    }
    
     /**
     * 
     */ 	
    public void setUdef2(String udef2){
        this.udef2=udef2;
    }
    
    /**
     * 
     */ 	
	public String getUdef3(){
        return udef3;
    }
    
     /**
     * 
     */ 	
    public void setUdef3(String udef3){
        this.udef3=udef3;
    }
    
    /**
     * 
     */ 	
	public String getUdef4(){
        return udef4;
    }
    
     /**
     * 
     */ 	
    public void setUdef4(String udef4){
        this.udef4=udef4;
    }
    
    /**
     * 
     */ 	
	public String getUdef5(){
        return udef5;
    }
    
     /**
     * 
     */ 	
    public void setUdef5(String udef5){
        this.udef5=udef5;
    }
    
    /**
     * 
     */ 	
	public String getUdef6(){
        return udef6;
    }
    
     /**
     * 
     */ 	
    public void setUdef6(String udef6){
        this.udef6=udef6;
    }
    
    /**
     * 
     */ 	
	public String getUdef7(){
        return udef7;
    }
    
     /**
     * 
     */ 	
    public void setUdef7(String udef7){
        this.udef7=udef7;
    }
    
    /**
     * 
     */ 	
	public String getUdef8(){
        return udef8;
    }
    
     /**
     * 
     */ 	
    public void setUdef8(String udef8){
        this.udef8=udef8;
    }
    
    /**
     * 
     */ 	
	public String getUdef9(){
        return udef9;
    }
    
     /**
     * 
     */ 	
    public void setUdef9(String udef9){
        this.udef9=udef9;
    }
    
    /**
     * 
     */ 	
	public String getUdef10(){
        return udef10;
    }
    
     /**
     * 
     */ 	
    public void setUdef10(String udef10){
        this.udef10=udef10;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("Year",getYear())
           .append("Name",getName())
           .append("Description",getDescription())
           .append("Status",getStatus())
           .append("StartTime",getStartTime())
           .append("EndTime",getEndTime())
           .append("CreateTime",getCreateTime())
           .append("CreateUser",getCreateUser())
           .append("UpdateTime",getUpdateTime())
           .append("UpdateUser",getUpdateUser())
           .append("Udef1",getUdef1())
           .append("Udef2",getUdef2())
           .append("Udef3",getUdef3())
           .append("Udef4",getUdef4())
           .append("Udef5",getUdef5())
           .append("Udef6",getUdef6())
           .append("Udef7",getUdef7())
           .append("Udef8",getUdef8())
           .append("Udef9",getUdef9())
           .append("Udef10",getUdef10())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ActivityInfoInfo == false) return false;
		if(this == obj) return true;
		ActivityInfoInfo other = (ActivityInfoInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

