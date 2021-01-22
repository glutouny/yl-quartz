package com.yl.quartz.demo.config.propertites;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 线程池参数配置
 *
 * @author li.yang01@hand-china.com 2021/1/15 11:37 上午
 */
@Component
@ConfigurationProperties(prefix = "o2.thread")
@Data
public class ThreadPoolProperties {

    private int corePoolSize = 10;

    private int maxPoolSize = 20;

    private int queueCapacity = 200;

    private String namePrefix = "o2-thread";

    private int keepAliveSeconds = 3000;
}
