package com.lean.stock.common.service;

import com.baomidou.mybatisplus.service.impl.*;
import com.lean.stock.common.dao.*;
import com.lean.stock.common.entity.*;
import com.baomidou.mybatisplus.service.*;
import org.springframework.stereotype.*;
import com.baomidou.mybatisplus.mapper.*;
import java.util.*;

@Service
public class StockService extends ServiceImpl<StockMapper, Stock> implements IService<Stock>
{
    public Map<String, Stock> getAllStockMap() {
        final Map<String, Stock> map = new HashMap<String, Stock>();
        final Wrapper<Stock> wrapper = (Wrapper<Stock>)new EntityWrapper();
        wrapper.setSqlSelect("code, name, industry,pe,esp,profit,pb");
        final List<Stock> list = (List<Stock>)this.selectList((Wrapper)wrapper);
        for (final Stock stock : list) {
            map.put(stock.getCode(), stock);
        }
        return map;
    }
}
