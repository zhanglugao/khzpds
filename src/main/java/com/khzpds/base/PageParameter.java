package com.khzpds.base;

import java.util.Map;

 

public class PageParameter
{
	public static final int DEFAULT_PAGE_SIZE = 10;

	private int pageSize;
	private int currentPage;
	private int totalPage;
	private int totalCount;
	private String orderField;
	private String orderType;
	private Map<String, String> search;
	private Map<Object,Object> select;
	
	public PageParameter()
	{
		this.currentPage = 1;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}

	public PageParameter(int currentPage, int pageSize)
	{
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public Map<String, String> getSearch() {
        return search;
    }

    public void setSearch(Map<String, String> search) {
        this.search = search;
    }
    
	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

	public Map<Object, Object> getSelect() {
		return select;
	}

	public void setSelect(Map<Object, Object> select) {
		this.select = select;
	}
	
}
