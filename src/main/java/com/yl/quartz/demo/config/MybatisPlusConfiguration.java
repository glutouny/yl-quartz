package com.yl.quartz.demo.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


/**
 * MybatisPlus配置类
 *
 *  @author li.yang01@hand-china.com 2021/1/15 10:35 上午
 */
@Configuration
@MapperScan("com.yl.quartz.demo.infra.mapper")
public class MybatisPlusConfiguration {


}
