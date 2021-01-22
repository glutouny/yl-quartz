package com.yl.quartz.demo.job;

import com.yl.quartz.demo.infra.util.DateUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;


/**
 * 测试JOB
 *
 * @author li.yang01@hand-china.com 2021/1/15 3:38 下午
 */
public class TestJob extends QuartzJobBean implements InterruptableJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestJob.class);

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info(DateUtils.convertToString(new Date()));
    }
}
