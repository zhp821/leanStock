package com.lean.stock.common.dto;

public class ResultDTO<T>
{
    private Integer status;
    private String msg;
    private T result;
    
    public ResultDTO(final Integer status, final String msg, final T result) {
        this.status = status;
        this.msg = msg;
        this.result = result;
    }
    
    public T getResult() {
        return this.result;
    }
    
    public void setResult(final T result) {
        this.result = result;
    }
    
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(final Integer status) {
        this.status = status;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
}
