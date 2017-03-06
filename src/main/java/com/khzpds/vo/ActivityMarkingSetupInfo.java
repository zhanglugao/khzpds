
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class ActivityMarkingSetupInfo implements Serializable
{
	
	private String id;   
	private String activityId;   
	private String itemType;   
	private String name;   
	private Double score;   
	private Integer sort;   
	private String vdef1;   
	private String vdef2;   
	private String vdef3;   
    
    public ActivityMarkingSetupInfo(){}
   
    
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
     * 项目类型字典表
     */ 	
	public String getItemType(){
        return itemType;
    }
    
     /**
     * 项目类型字典表
     */ 	
    public void setItemType(String itemType){
        this.itemType=itemType;
    }
    
    /**
     * 打分标准名称
     */ 	
	public String getName(){
        return name;
    }
    
     /**
     * 打分标准名称
     */ 	
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * 标准的最大分值
     */ 	
	public Double getScore(){
        return score;
    }
    
     /**
     * 标准的最大分值
     */ 	
    public void setScore(Double score){
        this.score=score;
    }
    
    /**
     * 排序
     */ 	
	public Integer getSort(){
        return sort;
    }
    
     /**
     * 排序
     */ 	
    public void setSort(Integer sort){
        this.sort=sort;
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
           .append("ItemType",getItemType())
           .append("Name",getName())
           .append("Score",getScore())
           .append("Sort",getSort())
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
		if(obj instanceof ActivityMarkingSetupInfo == false) return false;
		if(this == obj) return true;
		ActivityMarkingSetupInfo other = (ActivityMarkingSetupInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

