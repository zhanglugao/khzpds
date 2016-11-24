
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class RoleMenuInfo implements Serializable
{
	
	private String roleId;   
	private String menuId;   
    
    public RoleMenuInfo(){}
   
    
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
    
    /**
     * 
     */ 	
	public String getMenuId(){
        return menuId;
    }
    
     /**
     * 
     */ 	
    public void setMenuId(String menuId){
        this.menuId=menuId;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("RoleId",getRoleId())
           .append("MenuId",getMenuId())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMenuId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RoleMenuInfo == false) return false;
		if(this == obj) return true;
		RoleMenuInfo other = (RoleMenuInfo)obj;
		return new EqualsBuilder()
			.append(getMenuId(),other.getMenuId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

