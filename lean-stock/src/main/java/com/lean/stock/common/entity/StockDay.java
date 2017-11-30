package com.lean.stock.common.entity;

import com.baomidou.mybatisplus.annotations.*;
import java.util.*;

@TableName("stock_day")
public class StockDay
{
    @TableId("id")
    Long id;
    String code;
    String open;
    String close;
    
    String date;
    String volume;
    String high;
    String low;
    transient String des;
    transient int status;
    transient String url;
    transient String name;
    transient List<Float> preLowPriceList=new ArrayList<Float>();;
    
    public StockDay(){
    	super();
    }
     
    
    public  StockDay(final String code, final String open, final String close, final String date, final String volume, final String high, final String low) {
        this.des = new String();
        this.status = -100;
        this.url = new String();
        this.name = new String();
        this.preLowPriceList = new ArrayList<Float>();
        this.code = code;
        this.open = open;
        this.close = close;
        this.date = date;
        this.volume = volume;
        this.high = high;
        this.low = low;
    }    

	public void sortLowToPriceList() {
        Collections.sort(this.preLowPriceList);
    }
    
    public String getDes() {
        return this.des + "@@@\u524d\u51e0\u5929\u4ef7\u683c\u6392\u5e8f" + this.preLowPriceList.toString();
    }
    
    public String getCode() {
        return this.code;
    }
    
    public void addLowToPriceList(final Float low) {
        this.preLowPriceList.add(low);
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    public String getOpen() {
        return this.open;
    }
    
    public void setOpen(final String open) {
        this.open = open;
    }
    
    public String getClose() {
        return this.close;
    }
    
    public void setClose(final String close) {
        this.close = close;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public void setDate(final String date) {
        this.date = date;
    }
    
    public String getVolume() {
        return this.volume;
    }
    
    public void setVolume(final String volume) {
        this.volume = volume;
    }
    
    public String getHigh() {
        return this.high;
    }
    
    public void setHigh(final String high) {
        this.high = high;
    }
    
    public String getLow() {
        return this.low;
    }
    
    public void setLow(final String low) {
        this.low = low;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(final Long id) {
        this.id = id;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    public float getOnePrice(final int i) {
        if (this.preLowPriceList.size() > i) {
            return this.preLowPriceList.get(i);
        }
        return 100000.0f;
    }
    
    public void addDes(final String msg) {
        this.des += msg;
    }
    
    @Override
    public String toString() {
        return "StockDay [id=" + this.id + ", code=" + this.code + ", open=" + this.open + ", close=" + this.close + ", date=" + this.date + ", volume=" + this.volume + ", high=" + this.high + ", low=" + this.low + ", des=" + this.getDes() + ", status=" + this.status + "]";
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
