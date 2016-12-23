
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class ManagerOrgInfo implements Serializable
{
	
	private String managerId;   
	private String orgId;   
    
    public ManagerOrgInfo(){}
   
    
    /**
     * 
     */ 	
	public String getManagerId(){
        return managerId;
    }
    
     /**
     * 
     */ 	
    public void setManagerId(String managerId){
        this.managerId=managerId;
    }
    
    /**
     * 
     */ 	
	public String getOrgId(){
        return orgId;
    }
    
     /**
     * 
     */ 	
    public void setOrgId(String orgId){
        this.orgId=orgId;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("ManagerId",getManagerId())
           .append("OrgId",getOrgId())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getOrgId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ManagerOrgInfo == false) return false;
		if(this == obj) return true;
		ManagerOrgInfo other = (ManagerOrgInfo)obj;
		return new EqualsBuilder()
			.append(getOrgId(),other.getOrgId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

