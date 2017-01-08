

package com.khzpds.vo;
import java.io.Serializable;
import java.util.List;

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
	private Integer sort;   
    private Integer level;
    private String parentId;
    private String vdef1;
    private String vdef2;
    private String vdef3;
    
	
    public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getVdef1() {
		return vdef1;
	}


	public void setVdef1(String vdef1) {
		this.vdef1 = vdef1;
	}


	public String getVdef2() {
		return vdef2;
	}


	public void setVdef2(String vdef2) {
		this.vdef2 = vdef2;
	}


	public String getVdef3() {
		return vdef3;
	}


	public void setVdef3(String vdef3) {
		this.vdef3 = vdef3;
	}


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
    
    /**
     * 
     */ 	
	public Integer getSort(){
        return sort;
    }
    
     /**
     * 
     */ 	
    public void setSort(Integer sort){
        this.sort=sort;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("Name",getName())
           .append("Url",getUrl())
           .append("Sort",getSort())
           .append("Level",getLevel())
           .append("ParentId",getParentId())
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
		if(obj instanceof MenuInfo == false) return false;
		if(this == obj) return true;
		MenuInfo other = (MenuInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***///
	private String roleId;
	private List<MenuInfo> childMenus;
	private String parentName;
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	public List<MenuInfo> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<MenuInfo> childMenus) {
		this.childMenus = childMenus;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	//--CustomEnd*****///
}

