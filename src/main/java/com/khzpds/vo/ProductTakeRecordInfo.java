
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class ProductTakeRecordInfo implements Serializable
{
	
	private String id;   
	private String activityId;   
	private String itemId;   
	private String applyId;   
	private String takeUserId;   
	private java.util.Date takeTime;   
	private String takeType;   
	private Double takeScore;   
	private String def1;   
	private String def2;   
	private String def3;   
	private String def4;   
    
    public ProductTakeRecordInfo(){}
   
    
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
     * 报名作品id
     */ 	
	public String getApplyId(){
        return applyId;
    }
    
     /**
     * 报名作品id
     */ 	
    public void setApplyId(String applyId){
        this.applyId=applyId;
    }
    
    /**
     * 打分人id
     */ 	
	public String getTakeUserId(){
        return takeUserId;
    }
    
     /**
     * 打分人id
     */ 	
    public void setTakeUserId(String takeUserId){
        this.takeUserId=takeUserId;
    }
    
    /**
     * 打分时间
     */ 	
	public java.util.Date getTakeTime(){
        return takeTime;
    }
    
     /**
     * 打分时间
     */ 	
    public void setTakeTime(java.util.Date takeTime){
        this.takeTime=takeTime;
    }
    
    /**
     * 打分类型 专家or网络投票
     */ 	
	public String getTakeType(){
        return takeType;
    }
    
     /**
     * 打分类型 专家or网络投票
     */ 	
    public void setTakeType(String takeType){
        this.takeType=takeType;
    }
    
    /**
     * 打分分值
     */ 	
	public Double getTakeScore(){
        return takeScore;
    }
    
     /**
     * 打分分值
     */ 	
    public void setTakeScore(Double takeScore){
        this.takeScore=takeScore;
    }
    
    /**
     * 
     */ 	
	public String getDef1(){
        return def1;
    }
    
     /**
     * 
     */ 	
    public void setDef1(String def1){
        this.def1=def1;
    }
    
    /**
     * 
     */ 	
	public String getDef2(){
        return def2;
    }
    
     /**
     * 
     */ 	
    public void setDef2(String def2){
        this.def2=def2;
    }
    
    /**
     * 
     */ 	
	public String getDef3(){
        return def3;
    }
    
     /**
     * 
     */ 	
    public void setDef3(String def3){
        this.def3=def3;
    }
    
    /**
     * 
     */ 	
	public String getDef4(){
        return def4;
    }
    
     /**
     * 
     */ 	
    public void setDef4(String def4){
        this.def4=def4;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("ActivityId",getActivityId())
           .append("ItemId",getItemId())
           .append("ApplyId",getApplyId())
           .append("TakeUserId",getTakeUserId())
           .append("TakeTime",getTakeTime())
           .append("TakeType",getTakeType())
           .append("TakeScore",getTakeScore())
           .append("Def1",getDef1())
           .append("Def2",getDef2())
           .append("Def3",getDef3())
           .append("Def4",getDef4())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductTakeRecordInfo == false) return false;
		if(this == obj) return true;
		ProductTakeRecordInfo other = (ProductTakeRecordInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

