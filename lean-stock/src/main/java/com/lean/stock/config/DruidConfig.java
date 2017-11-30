package com.lean.stock.config;

import javax.sql.*;
import com.alibaba.druid.spring.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

@Configuration
public class DruidConfig
{
    @Bean
    public DataSource dataSourceOne() {
        return (DataSource)DruidDataSourceBuilder.create().build();
    }
}
