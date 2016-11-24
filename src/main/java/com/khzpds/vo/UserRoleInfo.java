
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class UserRoleInfo implements Serializable
{
	
	private String userId;   
	private String roleId;   
    
    public UserRoleInfo(){}
   
    
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
	public String getRoleId(){
        return roleId;
    }
    
     /**
     * 
     */ 	
    public void setRoleId(String roleId){
        this.roleId=roleId;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("UserId",getUserId())
           .append("RoleId",getRoleId())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRoleId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserRoleInfo == false) return false;
		if(this == obj) return true;
		UserRoleInfo other = (UserRoleInfo)obj;
		return new EqualsBuilder()
			.append(getRoleId(),other.getRoleId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

