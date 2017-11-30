package com.lean.stock.ai.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lean.stock.common.dao.StockDayMapper;
import com.lean.stock.common.entity.Stock;
import com.lean.stock.common.entity.StockDay;
import com.lean.stock.common.service.StockService;
import com.lean.stock.util.DateUtil;
import com.lean.stock.util.StockUtil;

@Service
public class BandStrategyService extends ServiceImpl<StockDayMapper, StockDay> implements IService<StockDay> {
	private static final Logger LOGGER;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private StockService stockService;

	public List<StockDay> getAllStockDayInfoBetweenDay(final String begin, final String end, final String codes) {
		BandStrategyService.LOGGER.info("it will fetch [{}] stock Day info between [{}] ~ [{}] ",
				new Object[] { codes, begin, end });
		final Wrapper<StockDay> wrapper = (Wrapper<StockDay>) new EntityWrapper<StockDay>();
		wrapper.between("date", begin, end);
		if (!StringUtils.isEmpty((Object) codes)) {
			wrapper.in("code", codes);
		}
		return (List<StockDay>) this.selectList((Wrapper<StockDay>) wrapper);
	}

	public List<StockDay> continueLowWithDays(final String codes, final int days, final String time,
			final int lowPriceDay) throws ParseException {
		final Map<String, Stock> allStock = (Map<String, Stock>) this.stockService.getAllStockMap();
		final Wrapper<StockDay> wrapper = (Wrapper<StockDay>) new EntityWrapper<StockDay>();
		if (!StringUtils.isEmpty((Object) codes)) {
			wrapper.in("code", codes);
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.df.parse(time));
		calendar.add(5, -lowPriceDay - (2 * lowPriceDay / 7 + 10));
		wrapper.le("date", time);
		wrapper.ge("date", df.format(calendar.getTime()));
		wrapper.orderBy("date", false);
		List<StockDay> list = this.selectList(wrapper);
		Map<String, List<StockDay>> map = new HashMap<String, List<StockDay>>();
		Map<String, StockDay> timeMap = new HashMap<String, StockDay>();
		for (final StockDay stock : list) {
			if (!timeMap.containsKey(stock.getCode())) {
				timeMap.put(stock.getCode(), stock);
			} else {
				if (!map.containsKey(stock.getCode())) {
					final List<StockDay> preList = new ArrayList<StockDay>();
					map.put(stock.getCode(), preList);
				}
				map.get(stock.getCode()).add(stock);
			}
		}
		final List<StockDay> resultList = new ArrayList<StockDay>();
		for (final StockDay stock2 : timeMap.values()) {
			final Stock s = allStock.get(stock2.getCode());
			if (s == null) {
				continue;
			}
			if (s.getProfit() < 0.0f || s.getEsp() < 0.0f || s.getPe() > 100.0f) {
				continue;
			}
			if (s.getPe() < 1.0f) {
				continue;
			}
			final List<StockDay> preList2 = map.get(stock2.getCode());
			for (int i = 0; i <= days && preList2 != null; ++i) {
				if (preList2.size() < i + 1) {
					break;
				}
				final float close = Float.parseFloat(preList2.get(i).getClose());
				final float open = Float.parseFloat(preList2.get(i).getOpen());
				if (close >= open) {
					break;
				}
				if (Float.parseFloat(stock2.getClose()) > open) {
					break;
				}
			}
			this.buildLowList(lowPriceDay, allStock, map, resultList, stock2);
		}
		return resultList;
	}

	private void buildLowList(final int lowPriceDay, final Map<String, Stock> allStock,
			final Map<String, List<StockDay>> map, final List<StockDay> resultList, final StockDay stock) {
		final Stock obj = allStock.get(stock.getCode());
		if (obj != null) {
			stock.setName(obj.getName());
		}
		final List<StockDay> preList = map.get(stock.getCode());
		for (int i = 0; i <= lowPriceDay && preList != null && preList.size() >= i + 1; ++i) {
			stock.addLowToPriceList(Float.parseFloat(preList.get(i).getLow()));
		}
		stock.sortLowToPriceList();
		stock.setUrl(StockUtil.getSinaStockUrl(stock.getCode()));
		if (Float.parseFloat(stock.getLow()) <= stock.getOnePrice(0)) {
			stock.addDes("\u5efa\u8bae\u4e70\u5165\u4ef7\u4f4e\u4e8e" + stock.getOnePrice(0));
			resultList.add(stock);
		}
	}

	public List<StockDay> checkContinueLowWithDays(final String codes, final int days, final String time,
			final int lowPriceDay) throws ParseException {
		final List<StockDay> list = this.continueLowWithDays(codes, days, time, lowPriceDay);
		final Map<String, List<StockDay>> map = this.getMapAfterOneDayStock(codes, time, 25);
		for (final StockDay stock : list) {
			float price = stock.getOnePrice(1);
			float after1Price = -1.0f;
			float after2Price = -1.0f;
			float after3Price = -1.0f;
			float after4Price = -1.0f;
			float after5Price = -1.0f;
			float after6Price = -1.0f;
			try {
				after1Price = Float.parseFloat(map.get(stock.getCode()).get(0).getHigh());
				after2Price = Float.parseFloat(map.get(stock.getCode()).get(1).getHigh());
				after3Price = Float.parseFloat(map.get(stock.getCode()).get(2).getHigh());
				after4Price = Float.parseFloat(map.get(stock.getCode()).get(3).getHigh());
				after5Price = Float.parseFloat(map.get(stock.getCode()).get(4).getHigh());
				after6Price = Float.parseFloat(map.get(stock.getCode()).get(5).getHigh());
			} catch (Exception e) {
				stock.addDes("after price is error .... ");
			} finally {
				stock.addDes("@@@\u540e6\u5929\u4ef7\u683c@@@@@1:" + after1Price + ";@2:" + after2Price + ";@3:"
						+ after3Price + ";@4:" + after4Price + ";@5:" + after5Price + "@6:" + after6Price);
			}
			try {
				if (Float.parseFloat(stock.getLow()) > price) {
					stock.setStatus(-99);
					BandStrategyService.LOGGER.info("," + stock.getStatus() + "," + stock.toString());
					continue;
				}
				if (Float.parseFloat(stock.getOpen()) < price) {
					price = Float.parseFloat(stock.getOpen());
				}
				stock.setStatus(-1);
				if ((after1Price - price) / price > 0.005) {
					stock.setStatus(1);
					BandStrategyService.LOGGER.info("," + stock.getStatus() + "," + stock.toString());
					continue;
				}
				if ((after2Price - price) / price > 0.005) {
					stock.setStatus(2);
					BandStrategyService.LOGGER.info("," + stock.getStatus() + "," + stock.toString());
					continue;
				}
				if ((after3Price - price) / price > 0.005) {
					stock.setStatus(3);
					BandStrategyService.LOGGER.info("," + stock.getStatus() + "," + stock.toString());
					continue;
				}
			} catch (Exception ex) {
			}
			BandStrategyService.LOGGER.info("," + stock.getStatus() + "," + stock.toString());
		}
		return list;
	}

	private Map<String, List<StockDay>> getMapAfterOneDayStock(final String codes, final String time, final int days)
			throws ParseException {
		final List<StockDay> afterAllList = this.getAfterOneDayStockList(codes, time, days);
		final Map<String, List<StockDay>> map = new HashMap<String, List<StockDay>>();
		for (final StockDay stock : afterAllList) {
			if (!map.containsKey(stock.getCode())) {
				final List<StockDay> afterList = new ArrayList<StockDay>();
				map.put(stock.getCode(), afterList);
			}
			map.get(stock.getCode()).add(stock);
		}
		return map;
	}

	private List<StockDay> getAfterOneDayStockList(final String codes, final String time, final int days)
			throws ParseException {
		final Wrapper<StockDay> wrapper = (Wrapper<StockDay>) new EntityWrapper<StockDay>();
		if (!StringUtils.isEmpty((Object) codes)) {
			wrapper.in("code", codes);
		}
		wrapper.le("date", (Object) DateUtil.addNumDaysToTime(days, time));
		wrapper.gt("date", (Object) time);
		wrapper.orderBy("date", true);
		final List<StockDay> afterAllList = (List<StockDay>) this.selectList((Wrapper<StockDay>) wrapper);
		return afterAllList;
	}

	public List<StockDay> checkLowPriceAndSecondSale(final String codes, final int days, final String time,
			final int lowPriceDay) throws ParseException {
		final List<StockDay> list = this.continueLowWithDays(codes, days, time, lowPriceDay);
		final List<StockDay> buyList = new ArrayList<StockDay>();
		final Map<String, List<StockDay>> map = this.getMapAfterOneDayStock(codes, time, 10);
		final Map<String, Stock> allStock = (Map<String, Stock>) this.stockService.getAllStockMap();
		for (final StockDay stock : list) {
			try {
				float lowPrice = stock.getOnePrice(0);
				final float low = Float.parseFloat(stock.getLow());
				final float open = Float.parseFloat(stock.getOpen());
				final float close = Float.parseFloat(stock.getClose());
				final Stock s = allStock.get(stock.getCode());
				if (s == null) {
					continue;
				}
				if (s.getProfit() < 0.0f || s.getEsp() < 0.0f || s.getPe() > 100.0f || s.getPe() < 1.0f) {
					continue;
				}
				if (low > lowPrice) {
					continue;
				}
				if (open < lowPrice) {
					lowPrice = close;
				}
				final float afterPrice = Float.parseFloat(map.get(stock.getCode()).get(0).getHigh());
				if (afterPrice > lowPrice) {
					stock.setStatus(1);
				} else {
					stock.setStatus(-1);
				}
				stock.addDes(" " + stock.getDate() + " buy price is " + lowPrice + " and "
						+ map.get(stock.getCode()).get(0).getDate() + " high is " + afterPrice);
				stock.setName(s.getName());
				buyList.add(stock);
			} catch (Exception ex) {
			}
		}
		return buyList;
	}

	static {
		LOGGER = LoggerFactory.getLogger((Class) BandStrategyService.class);
	}
}
