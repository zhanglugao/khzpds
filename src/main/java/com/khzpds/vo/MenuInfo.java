
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class MenuInfo implements Serializable
{
	
	private String id;   
	private String name;   
	private String url;   
    
    public MenuInfo(){}
   
    
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
     * 
     */ 	
	public String getName(){
        return name;
    }
    
     /**
     * 
     */ 	
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * 
     */ 	
	public String getUrl(){
        return url;
    }
    
     /**
     * 
     */ 	
    public void setUrl(String url){
        this.url=url;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("Name",getName())
           .append("Url",getUrl())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MenuInfo == false) return false;
		if(this == obj) return true;
		MenuInfo other = (MenuInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***///
	private String roleId;

	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	//--CustomEnd*****///
}

