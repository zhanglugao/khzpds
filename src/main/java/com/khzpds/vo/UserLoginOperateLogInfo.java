
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class UserLoginOperateLogInfo implements Serializable
{
	
	private String id;   
	private String type;   
	private String userId;   
	private java.util.Date operateTime;   
	private String desc;   
	private String resourceId;   
	private String resourceType;   
	private String vdef1;   
	private String vdef2;   
	private String vdef3;   
	private String vdef4;   
	private String vdef5;   
    
    public UserLoginOperateLogInfo(){}
   
    
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
     * 登录 增删改 共4种
     */ 	
	public String getType(){
        return type;
    }
    
     /**
     * 登录 增删改 共4种
     */ 	
    public void setType(String type){
        this.type=type;
    }
    
    /**
     * 操作人id
     */ 	
	public String getUserId(){
        return userId;
    }
    
     /**
     * 操作人id
     */ 	
    public void setUserId(String userId){
        this.userId=userId;
    }
    
    /**
     * 操作时间
     */ 	
	public java.util.Date getOperateTime(){
        return operateTime;
    }
    
     /**
     * 操作时间
     */ 	
    public void setOperateTime(java.util.Date operateTime){
        this.operateTime=operateTime;
    }
    
    /**
     * 备注
     */ 	
	public String getDesc(){
        return desc;
    }
    
     /**
     * 备注
     */ 	
    public void setDesc(String desc){
        this.desc=desc;
    }
    
    /**
     * 操作的资源id
     */ 	
	public String getResourceId(){
        return resourceId;
    }
    
     /**
     * 操作的资源id
     */ 	
    public void setResourceId(String resourceId){
        this.resourceId=resourceId;
    }
    
    /**
     * 资源类型 如活动 项目 等等
     */ 	
	public String getResourceType(){
        return resourceType;
    }
    
     /**
     * 资源类型 如活动 项目 等等
     */ 	
    public void setResourceType(String resourceType){
        this.resourceType=resourceType;
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
    
    /**
     * 
     */ 	
	public String getVdef4(){
        return vdef4;
    }
    
     /**
     * 
     */ 	
    public void setVdef4(String vdef4){
        this.vdef4=vdef4;
    }
    
    /**
     * 
     */ 	
	public String getVdef5(){
        return vdef5;
    }
    
     /**
     * 
     */ 	
    public void setVdef5(String vdef5){
        this.vdef5=vdef5;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("Type",getType())
           .append("UserId",getUserId())
           .append("OperateTime",getOperateTime())
           .append("Desc",getDesc())
           .append("ResourceId",getResourceId())
           .append("ResourceType",getResourceType())
           .append("Vdef1",getVdef1())
           .append("Vdef2",getVdef2())
           .append("Vdef3",getVdef3())
           .append("Vdef4",getVdef4())
           .append("Vdef5",getVdef5())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserLoginOperateLogInfo == false) return false;
		if(this == obj) return true;
		UserLoginOperateLogInfo other = (UserLoginOperateLogInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

