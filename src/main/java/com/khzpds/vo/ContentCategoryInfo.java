

package com.khzpds.vo;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@SuppressWarnings("serial")
public class ContentCategoryInfo implements Serializable
{
	
	private Long id;   
	private String name;   
	private Long parentId;   
	private String description;   
	private Integer sort;   
	private String path;   
	private Boolean ifLeaf;   
	private String platformId;   
	private String code;   
	private String createUser;   
    
    public ContentCategoryInfo(){}
   
    
    /**
     * 分类id
     */ 	
	public Long getId(){
        return id;
    }
    
     /**
     * 分类id
     */ 	
    public void setId(Long id){
        this.id=id;
    }
    
    /**
     * 分类名字
     */ 	
	public String getName(){
        return name;
    }
    
     /**
     * 分类名字
     */ 	
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * 父分类id 0 root根节点 分类大类
     */ 	
	public Long getParentId(){
        return parentId;
    }
    
     /**
     * 父分类id 0 root根节点 分类大类
     */ 	
    public void setParentId(Long parentId){
        this.parentId=parentId;
    }
    
    /**
     * 分类描述
     */ 	
	public String getDescription(){
        return description;
    }
    
     /**
     * 分类描述
     */ 	
    public void setDescription(String description){
        this.description=description;
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
     * id拼装，横线-分割
     */ 	
	public String getPath(){
        return path;
    }
    
     /**
     * id拼装，横线-分割
     */ 	
    public void setPath(String path){
        this.path=path;
    }
    
    /**
     * 1叶子，0不是叶子
     */ 	
	public Boolean getIfLeaf(){
        return ifLeaf;
    }
    
     /**
     * 1叶子，0不是叶子
     */ 	
    public void setIfLeaf(Boolean ifLeaf){
        this.ifLeaf=ifLeaf;
    }
    
    /**
     * 平台的id(增加)
     */ 	
	public String getPlatformId(){
        return platformId;
    }
    
     /**
     * 平台的id(增加)
     */ 	
    public void setPlatformId(String platformId){
        this.platformId=platformId;
    }
    
    /**
     * 分类码 用于标识分类大类
     */ 	
	public String getCode(){
        return code;
    }
    
     /**
     * 分类码 用于标识分类大类
     */ 	
    public void setCode(String code){
        this.code=code;
    }
    
    /**
     * 所属用户
     */ 	
	public String getCreateUser(){
        return createUser;
    }
    
     /**
     * 所属用户
     */ 	
    public void setCreateUser(String createUser){
        this.createUser=createUser;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("Name",getName())
           .append("ParentId",getParentId())
           .append("Description",getDescription())
           .append("Sort",getSort())
           .append("Path",getPath())
           .append("IfLeaf",getIfLeaf())
           .append("PlatformId",getPlatformId())
           .append("Code",getCode())
           .append("CreateUser",getCreateUser())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ContentCategoryInfo == false) return false;
		if(this == obj) return true;
		ContentCategoryInfo other = (ContentCategoryInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***///
		public Long _parentId;
	    private String additional;//其他备注字段
	    private List<ContentCategoryInfo> son;
	    
		public List<ContentCategoryInfo> getSon() {
			return son;
		}

		public void setSon(List<ContentCategoryInfo> son) {
			this.son = son;
		}

		public String getAdditional() {
			return additional;
		}

		public void setAdditional(String additional) {
			this.additional = additional;
		}
	    
		//--CustomEnd*****///
}

