package com.yl.quartz.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据源配置
 *
 * @author li.yang01@hand-china.com 2021/1/15 11:33 上午
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public HikariConfig hikariConfig(@Value("${spring.datasource.hikari.pool-name}") String poolName,
                                     @Value("${spring.datasource.hikari.driver-class-name}")
                                             String driverClassName,
                                     @Value("${spring.datasource.hikari.jdbc-url}")
                                             String url,
                                     @Value("${spring.datasource.hikari.username}")
                                             String username,
                                     @Value("${spring.datasource.hikari.password}")
                                             String password,
                                     @Value("${spring.datasource.hikari.connection-timeout}")
                                             int connectionTimeout,
                                     @Value("${spring.datasource.hikari.maximum-pool-size}")
                                             int maxPoolSize,
                                     @Value("${spring.datasource.hikari.minimum-idle}")
                                             int minIdle,
                                     @Value("${spring.datasource.hikari.idle-timeout}")
                                             int idleTimeout,
                                     @Value("${spring.datasource.hikari.connection-test-query}")
                                             String testQuery,
                                     @Value("${spring.datasource.hikari.read-only}")
                                             boolean readOnly) {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName(poolName);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setConnectionTimeout(connectionTimeout);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        hikariConfig.setMinimumIdle(minIdle);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setConnectionTestQuery(testQuery);
        hikariConfig.setReadOnly(readOnly);
        return hikariConfig;
    }

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }


}
