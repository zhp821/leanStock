package com.lean.stock.config;

import org.mybatis.spring.annotation.*;
import com.baomidou.mybatisplus.plugins.*;
import com.baomidou.mybatisplus.enums.*;
import org.springframework.context.annotation.*;

@Configuration
@MapperScan({ "com.lean.**.dao*" })
public class MybatisPlusConfig
{
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        final PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectClazz(DBType.MYSQL.getDb());
        return paginationInterceptor;
    }
}
