package com.lean.stock.fetch.controller;

import com.lean.stock.fetch.service.*;
import org.springframework.beans.factory.annotation.*;
import com.lean.stock.common.dto.*;
import java.sql.*;
import org.springframework.web.bind.annotation.*;
import com.lean.stock.ai.controller.*;
import org.slf4j.*;

@RestController
@RequestMapping({ "/api/v1/data/fetch" })
public class FetchDataController
{
    private static final Logger logger;
    @Autowired
    private SimpleThreadImportDataToDbService service;
    
    @RequestMapping(value = { "/db" }, method = { RequestMethod.GET })
    public ResultDTO<String> stockList(@RequestParam("file") final String file) throws SQLException {
        FetchDataController.logger.info("it will deal [{}] stock data info db ", (Object)file);
        final int num = this.service.start(file);
        final ResultDTO<String> result = new ResultDTO<String>(0, "save To Db", "it save " + num + " to DB");
        return result;
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)BandStratetyController.class);
    }
}
