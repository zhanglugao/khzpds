package com.khzpds.vo;
import java.util.List;

public class TreeGridJsonVo<T> {
    private Integer total;
    private List<T> rows;
    private List<T> footer;
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    public List<T> getFooter() {
        return footer;
    }
    public void setFooter(List<T> footer) {
        this.footer = footer;
    }
    
}
