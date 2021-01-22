package com.yl.quartz.demo.config;

import com.yl.quartz.demo.config.propertites.ThreadPoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * 
 * @author li.yang01@hand-china.com 2021/1/15 11:38 上午
 */
@Configuration
@EnableConfigurationProperties({ThreadPoolProperties.class})
public class ThreadPoolTaskConfig {

    @Bean("ylThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor orderThreadPoolTaskExecutor (final ThreadPoolProperties threadPoolProperties) {
        ThreadPoolTaskExecutor orderThreadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数量
        orderThreadPoolTaskExecutor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        //最大线程数量
        orderThreadPoolTaskExecutor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        //队列中最大任务数
        orderThreadPoolTaskExecutor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        //线程名称前缀
        orderThreadPoolTaskExecutor.setThreadNamePrefix(threadPoolProperties.getNamePrefix());
        //线程空闲后最大空闲时间
        orderThreadPoolTaskExecutor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        //当到达最大线程数是如何处理新任务
        orderThreadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return orderThreadPoolTaskExecutor;
    }
}
