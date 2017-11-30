package com.lean.stock.common.entity;

import com.baomidou.mybatisplus.annotations.*;

@TableName("stock")
public class Stock
{
    String code;
    String name;
    String industry;
    float pe;
    float pb;
    float esp;
    float profit;
    
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getIndustry() {
        return this.industry;
    }
    
    public void setIndustry(final String industry) {
        this.industry = industry;
    }
    
    public float getPe() {
        return this.pe;
    }
    
    public float getPb() {
        return this.pb;
    }
    
    public float getEsp() {
        return this.esp;
    }
    
    public float getProfit() {
        return this.profit;
    }
}
