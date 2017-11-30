package com.lean.stock.ai.controller;

import com.lean.stock.ai.service.*;
import org.springframework.beans.factory.annotation.*;
import com.lean.stock.common.dto.*;
import com.lean.stock.common.entity.*;
import org.springframework.web.bind.annotation.*;
import java.text.*;
import com.lean.stock.util.*;
import java.util.*;
import org.slf4j.*;

@RestController
@RequestMapping({ "/api/v1/strategy/band" })
public class BandStratetyController
{
    private static final Logger logger;
    @Autowired
    private BandStrategyService service;
    
    @RequestMapping(value = { "/list" }, method = { RequestMethod.GET })
    public ResultDTO<List<StockDay>> stockList(@RequestParam("begin") final String begin, @RequestParam("end") final String end, @RequestParam(value = "codes", required = false) final String codes) {
        BandStratetyController.logger.info("it will fetch [{}] stock Day info between [{}] ~ [{}] ", new Object[] { codes, begin, end });
        final List<StockDay> list = (List<StockDay>)this.service.getAllStockDayInfoBetweenDay(begin, end, codes);
        final ResultDTO<List<StockDay>> result = (ResultDTO<List<StockDay>>)new ResultDTO(0, "", (Object)list);
        return result;
    }
    
    @RequestMapping(value = { "/lowlist" }, method = { RequestMethod.GET })
    public ResultDTO<List<StockDay>> stockBandLowList(@RequestParam("time") final String time, @RequestParam("lowPriceDay") final int lowPriceDay, @RequestParam("days") final int days, @RequestParam(value = "codes", required = false) final String codes) throws ParseException {
        BandStratetyController.logger.info("it will fetch on [{}] day and   [{}]  stock continue [{}] days low and Price is lower in pre [{}]", new Object[] { time, codes, days, lowPriceDay });
        final List<StockDay> list = (List<StockDay>)this.service.continueLowWithDays(codes, days, time, lowPriceDay);
        final ResultDTO<List<StockDay>> result = (ResultDTO<List<StockDay>>)new ResultDTO(0, "\u67d0\u65f6\u95f4\u524d\u8fde\u8dcc2\u5929\u7684price info," + list.size(), (Object)list);
        return result;
    }
    
    @RequestMapping(value = { "/checklowlist" }, method = { RequestMethod.GET })
    public ResultDTO<List<StockDay>> checkStockBandLowList(@RequestParam("start") final String start, @RequestParam("end") final String end, @RequestParam("lowPriceDay") final int lowPriceDay, @RequestParam("days") final int days, @RequestParam(value = "codes", required = false) final String codes) throws ParseException {
        BandStratetyController.logger.info("it will check  from [{}]  to [{}]  day  these code  [{}]  stock low [{}] price and prelowPriceDay [{}]  ", new Object[] { start, end, codes, days, lowPriceDay });
        final List<StockDay> list = new ArrayList<StockDay>();
        for (int num = DateUtil.daysBetween(start, end), i = 0; i <= num; ++i) {
            list.addAll(this.service.checkContinueLowWithDays(codes, days, DateUtil.addNumDaysToTimeString(i, start), lowPriceDay));
        }
        int s1 = 0;
        int s2 = 0;
        int s3 = 0;
        int s4 = 0;
        final int all = list.size();
        for (final StockDay stock : list) {
            if (stock.getStatus() == 1) {
                ++s1;
            }
            else if (stock.getStatus() == 2) {
                ++s2;
            }
            else if (stock.getStatus() == 3) {
                ++s3;
            }
            else {
                if (stock.getStatus() != -1) {
                    continue;
                }
                ++s4;
            }
        }
        final String msg = "all:" + all + ";success1:" + s1 + ";success2:" + s2 + ";success3:" + s3 + "failed:" + s4;
        final ResultDTO<List<StockDay>> result = (ResultDTO<List<StockDay>>)new ResultDTO(0, msg, (Object)list);
        return result;
    }
    
    @RequestMapping(value = { "/checkonedaybuy" }, method = { RequestMethod.GET })
    public ResultDTO<List<StockDay>> checkOneStockBandLowList(@RequestParam("start") final String start, @RequestParam("end") final String end, @RequestParam("lowPriceDay") final int lowPriceDay, @RequestParam("days") final int days, @RequestParam(value = "codes", required = false) final String codes) throws ParseException {
        final List<StockDay> list = new ArrayList<StockDay>();
        for (int num = DateUtil.daysBetween(start, end), i = 0; i <= num; ++i) {
            BandStratetyController.logger.info("begin deal with " + DateUtil.addNumDaysToTimeString(i, start));
            list.addAll(this.service.checkLowPriceAndSecondSale(codes, days, DateUtil.addNumDaysToTimeString(i, start), lowPriceDay));
        }
        int i = 0;
        for (final StockDay stock : list) {
            if (stock.getStatus() == 1) {
                ++i;
            }
        }
        final String msg = "all:" + list.size() + ";success1:" + i;
        final ResultDTO<List<StockDay>> result = (ResultDTO<List<StockDay>>)new ResultDTO(0, msg, (Object)list);
        return result;
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)BandStratetyController.class);
    }
}
