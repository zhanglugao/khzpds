
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class UserApplyVoteInfo implements Serializable
{
	
	private String id;   
	private String applyId;   
	private String userId;   
	private String openId;   
	private String ip;   
	private java.util.Date voteTime;   
	private String activityId;   
	private String itemId;   
	private String vdef1;   
	private String vdef2;   
	private String vdef3;   
    
    public UserApplyVoteInfo(){}
   
    
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
     * 报名id
     */ 	
	public String getApplyId(){
        return applyId;
    }
    
     /**
     * 报名id
     */ 	
    public void setApplyId(String applyId){
        this.applyId=applyId;
    }
    
    /**
     * 投票用户
     */ 	
	public String getUserId(){
        return userId;
    }
    
     /**
     * 投票用户
     */ 	
    public void setUserId(String userId){
        this.userId=userId;
    }
    
    /**
     * 微信端open_id
     */ 	
	public String getOpenId(){
        return openId;
    }
    
     /**
     * 微信端open_id
     */ 	
    public void setOpenId(String openId){
        this.openId=openId;
    }
    
    /**
     * 投票ip
     */ 	
	public String getIp(){
        return ip;
    }
    
     /**
     * 投票ip
     */ 	
    public void setIp(String ip){
        this.ip=ip;
    }
    
    /**
     * 投票时间
     */ 	
	public java.util.Date getVoteTime(){
        return voteTime;
    }
    
     /**
     * 投票时间
     */ 	
    public void setVoteTime(java.util.Date voteTime){
        this.voteTime=voteTime;
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
           .append("ApplyId",getApplyId())
           .append("UserId",getUserId())
           .append("OpenId",getOpenId())
           .append("Ip",getIp())
           .append("VoteTime",getVoteTime())
           .append("ActivityId",getActivityId())
           .append("ItemId",getItemId())
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
		if(obj instanceof UserApplyVoteInfo == false) return false;
		if(this == obj) return true;
		UserApplyVoteInfo other = (UserApplyVoteInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

