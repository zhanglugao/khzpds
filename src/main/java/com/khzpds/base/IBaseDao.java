package com.khzpds.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
 
public interface IBaseDao<T> {
    /**
     * 根据id查询对象
     */
    public T findById(@Param("id")Serializable id);
    
    /**
     * 根据联合主键查询对象
     */
    public T findById(java.util.Map<String, String> ids);
    
    /**
     * 根据实体查询对象
     */
    public List<T> findByParam(@Param("obj")T param);
    
     /**
     * 根据map查询对象并分�?
     */
    public List<T> findByParamForPage(@Param("obj")PageParameter param);
    
    
    /**
     * 添加对象
     * @param 对象
     * @return 对象id
     */
    public Integer insert(T param);
    
    /**
     * 更新对象
     * @param 对象
     * @return 影响条数
     */
    public Integer update(@Param("obj")T param);
    
    /**
     * 根据主键删除对象
     * @param 主键
     * @return 影响条数
     */
    public Integer deleteById(@Param("id")Serializable id);
    
    /**
     * 根据主键删除对象
     * @param 主键
     * @return 影响条数
     */
    public Integer deleteById(java.util.Map<String, String> keys);
    
    /**
     * 更新封存
     */
    public Integer updateSealFlag(Serializable id);
    
    /**
     * 更新封存根据联合主键
     */
    public Integer updateSealFlag(java.util.Map<String, String> keys);
    
}

