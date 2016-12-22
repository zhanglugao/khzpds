

package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@SuppressWarnings("serial")
public class CompetitionItemInfo implements Serializable
{
	
	private String id;   
	private String activityId;   
	private String name;   
	private String type;   
	private String status;   
	private String createUser;   
	private java.util.Date createTime;   
	private String updateUser;   
	private java.util.Date updateTime;   
	private java.util.Date publishTime;   
	private java.util.Date userApplyEndtime;   
	private java.util.Date firstReviewStarttime;   
	private java.util.Date firstReviewEndtime;   
	private java.util.Date secondReviewEndtime;   
    
    public CompetitionItemInfo(){}
   
    
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
     * 所属活动id
     */ 	
	public String getActivityId(){
        return activityId;
    }
    
     /**
     * 所属活动id
     */ 	
    public void setActivityId(String activityId){
        this.activityId=activityId;
    }
    
    /**
     * 名称
     */ 	
	public String getName(){
        return name;
    }
    
     /**
     * 名称
     */ 	
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * 类型
     */ 	
	public String getType(){
        return type;
    }
    
     /**
     * 类型
     */ 	
    public void setType(String type){
        this.type=type;
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
	public java.util.Date getPublishTime(){
        return publishTime;
    }
    
     /**
     * 
     */ 	
    public void setPublishTime(java.util.Date publishTime){
        this.publishTime=publishTime;
    }
    
    /**
     * 用户报名截止时间
     */ 	
	public java.util.Date getUserApplyEndtime(){
        return userApplyEndtime;
    }
    
     /**
     * 用户报名截止时间
     */ 	
    public void setUserApplyEndtime(java.util.Date userApplyEndtime){
        this.userApplyEndtime=userApplyEndtime;
    }
    
    /**
     * 一轮评审开始时间
     */ 	
	public java.util.Date getFirstReviewStarttime(){
        return firstReviewStarttime;
    }
    
     /**
     * 一轮评审开始时间
     */ 	
    public void setFirstReviewStarttime(java.util.Date firstReviewStarttime){
        this.firstReviewStarttime=firstReviewStarttime;
    }
    
    /**
     * 一轮评审结束时间
     */ 	
	public java.util.Date getFirstReviewEndtime(){
        return firstReviewEndtime;
    }
    
     /**
     * 一轮评审结束时间
     */ 	
    public void setFirstReviewEndtime(java.util.Date firstReviewEndtime){
        this.firstReviewEndtime=firstReviewEndtime;
    }
    
    /**
     * 二轮评审结束时间
     */ 	
	public java.util.Date getSecondReviewEndtime(){
        return secondReviewEndtime;
    }
    
     /**
     * 二轮评审结束时间
     */ 	
    public void setSecondReviewEndtime(java.util.Date secondReviewEndtime){
        this.secondReviewEndtime=secondReviewEndtime;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("ActivityId",getActivityId())
           .append("Name",getName())
           .append("Type",getType())
           .append("Status",getStatus())
           .append("CreateUser",getCreateUser())
           .append("CreateTime",getCreateTime())
           .append("UpdateUser",getUpdateUser())
           .append("UpdateTime",getUpdateTime())
           .append("PublishTime",getPublishTime())
           .append("UserApplyEndtime",getUserApplyEndtime())
           .append("FirstReviewStarttime",getFirstReviewStarttime())
           .append("FirstReviewEndtime",getFirstReviewEndtime())
           .append("SecondReviewEndtime",getSecondReviewEndtime())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CompetitionItemInfo == false) return false;
		if(this == obj) return true;
		CompetitionItemInfo other = (CompetitionItemInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***///
	private String statusName;

	public String getStatusName() {
		return statusName;
	}


	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	//--CustomEnd*****///
}

