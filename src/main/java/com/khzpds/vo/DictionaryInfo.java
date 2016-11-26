
package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class DictionaryInfo implements Serializable
{
	
	private String id;   
	private String parentId;   
	private String name;   
	private String code;   
	private String value;   
	private Integer sort;   
	private String description;   
    
    public DictionaryInfo(){}
   
    
    /**
     * (f_datadict_id)
     */ 	
	public String getId(){
        return id;
    }
    
     /**
     * (f_datadict_id)
     */ 	
    public void setId(String id){
        this.id=id;
    }
    
    /**
     * 父级id
     */ 	
	public String getParentId(){
        return parentId;
    }
    
     /**
     * 父级id
     */ 	
    public void setParentId(String parentId){
        this.parentId=parentId;
    }
    
    /**
     * 名称
     */ 	
	public String getName(){
        return name;
    }
    
     /**
     * 名称
     */ 	
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * 拼音简写，以及编码
     */ 	
	public String getCode(){
        return code;
    }
    
     /**
     * 拼音简写，以及编码
     */ 	
    public void setCode(String code){
        this.code=code;
    }
    
    /**
     * 
     */ 	
	public String getValue(){
        return value;
    }
    
     /**
     * 
     */ 	
    public void setValue(String value){
        this.value=value;
    }
    
    /**
     * 排序(f_sort_code)
     */ 	
	public Integer getSort(){
        return sort;
    }
    
     /**
     * 排序(f_sort_code)
     */ 	
    public void setSort(Integer sort){
        this.sort=sort;
    }
    
    /**
     * 描述(f_datadict_desc)
     */ 	
	public String getDescription(){
        return description;
    }
    
     /**
     * 描述(f_datadict_desc)
     */ 	
    public void setDescription(String description){
        this.description=description;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("ParentId",getParentId())
           .append("Name",getName())
           .append("Code",getCode())
           .append("Value",getValue())
           .append("Sort",getSort())
           .append("Description",getDescription())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DictionaryInfo == false) return false;
		if(this == obj) return true;
		DictionaryInfo other = (DictionaryInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***/////--CustomEnd*****///
}

