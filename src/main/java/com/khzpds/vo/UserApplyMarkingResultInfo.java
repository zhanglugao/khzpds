
package com.khzpds.vo;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class UserApplyMarkingResultInfo implements Serializable
{
	
	private String id;   
	private String activityId;   
	private String itemId;   
	private String itemType;   
	private String applyId;   
	private Double getScore;   
	private String markingUser;   
	private java.util.Date markingTime;   
	private String vdef1;   
	private String vdef2;   
	private String vdef3;   
    
    public UserApplyMarkingResultInfo(){}
   
    
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
     * 活动id
     */ 	
	public String getActivityId(){
        return activityId;
    }
    
     /**
     * 活动id
     */ 	
    public void setActivityId(String activityId){
        this.activityId=activityId;
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
     * 项目类型字典
     */ 	
	public String getItemType(){
        return itemType;
    }
    
     /**
     * 项目类型字典
     */ 	
    public void setItemType(String itemType){
        this.itemType=itemType;
    }
    
    /**
     * 作品id
     */ 	
	public String getApplyId(){
        return applyId;
    }
    
     /**
     * 作品id
     */ 	
    public void setApplyId(String applyId){
        this.applyId=applyId;
    }
    
    /**
     * 得分
     */ 	
	public Double getGetScore(){
        return getScore;
    }
    
     /**
     * 得分
     */ 	
    public void setGetScore(Double getScore){
        this.getScore=getScore;
    }
    
    /**
     * 评分人id
     */ 	
	public String getMarkingUser(){
        return markingUser;
    }
    
     /**
     * 评分人id
     */ 	
    public void setMarkingUser(String markingUser){
        this.markingUser=markingUser;
    }
    
    /**
     * 
     */ 	
	public java.util.Date getMarkingTime(){
        return markingTime;
    }
    
     /**
     * 
     */ 	
    public void setMarkingTime(java.util.Date markingTime){
        this.markingTime=markingTime;
    }
    
    /**
     * 
     */ 	
	public String getVdef1(){
        return vdef1;
    }
    
     /**
     * 
     */ 	
    public void setVdef1(String vdef1){
        this.vdef1=vdef1;
    }
    
    /**
     * 
     */ 	
	public String getVdef2(){
        return vdef2;
    }
    
     /**
     * 
     */ 	
    public void setVdef2(String vdef2){
        this.vdef2=vdef2;
    }
    
    /**
     * 
     */ 	
	public String getVdef3(){
        return vdef3;
    }
    
     /**
     * 
     */ 	
    public void setVdef3(String vdef3){
        this.vdef3=vdef3;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("ActivityId",getActivityId())
           .append("ItemId",getItemId())
           .append("ItemType",getItemType())
           .append("ApplyId",getApplyId())
           .append("GetScore",getGetScore())
           .append("MarkingUser",getMarkingUser())
           .append("MarkingTime",getMarkingTime())
           .append("Vdef1",getVdef1())
           .append("Vdef2",getVdef2())
           .append("Vdef3",getVdef3())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserApplyMarkingResultInfo == false) return false;
		if(this == obj) return true;
		UserApplyMarkingResultInfo other = (UserApplyMarkingResultInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***///
	private String setUpId;
	private String setUpName;
	private Double totalScore;
	
	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public String getSetUpId() {
		return setUpId;
	}

	public void setSetUpId(String setUpId) {
		this.setUpId = setUpId;
	}

	public String getSetUpName() {
		return setUpName;
	}

	public void setSetUpName(String setUpName) {
		this.setUpName = setUpName;
	}
	//--CustomEnd*****///
}

