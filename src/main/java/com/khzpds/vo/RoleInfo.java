
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class RoleInfo implements Serializable
{
	
	private String id;   
	private String name;   
    
    public RoleInfo(){}
   
    
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
     * 角色名称
     */ 	
	public String getName(){
        return name;
    }
    
     /**
     * 角色名称
     */ 	
    public void setName(String name){
        this.name=name;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("Name",getName())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RoleInfo == false) return false;
		if(this == obj) return true;
		RoleInfo other = (RoleInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

