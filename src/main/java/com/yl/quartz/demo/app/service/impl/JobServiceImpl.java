package com.yl.quartz.demo.app.service.impl;

import com.yl.quartz.demo.app.service.JobService;
import com.yl.quartz.demo.domain.base.BaseQuery;
import com.yl.quartz.demo.domain.entity.QuartzEntity;
import com.yl.quartz.demo.infra.exception.CommonException;
import com.yl.quartz.demo.infra.mapper.QuartzEntityMapper;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * JOB处理服务实现类
 *
 * @author li.yang01@hand-china.com 2021/1/15 11:48 上午
 */
@Service
public class JobServiceImpl implements JobService {

    private static final String TRIGGER_IDENTITY = "trigger";

    @Autowired
    private Scheduler scheduler;

    @Resource
    private QuartzEntityMapper quartzEntityMapper;

    @Override
    public void add(QuartzEntity entity) throws Exception {

        JobKey jobKey = new JobKey(entity.getJobName(),entity.getJobGroup());
        if (scheduler.checkExists(jobKey)) {
            throw new CommonException(entity.getJobName() + "exist in group " + entity.getJobGroup());
        }

        Class cls = Class.forName(entity.getJobClassName());

        JobBuilder jobBuilder = JobBuilder.newJob(cls).withIdentity(entity.getJobName(),entity.getJobGroup())
                .withDescription(entity.getDescription());

        // 添加job数据，在job类中可以通过context取出该数据，进行业务流程处理。
        if (null != entity.getConfigData()) {
            entity.getConfigData().forEach(jobBuilder::usingJobData);
        }
        // 保留存储，也就是说当执行完后，保留Job
        jobBuilder.storeDurably();
        JobDetail jobDetail = jobBuilder.build();

        // 调度器
        CronScheduleBuilder cronScheduleBuilder = null;
        if (StringUtils.isNotBlank(entity.getCronExpression())) {
            cronScheduleBuilder = CronScheduleBuilder.cronSchedule(entity.getCronExpression().trim());
        }
        // 触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(TRIGGER_IDENTITY + entity.getJobName(), entity.getJobGroup());

        //交由Scheduler安排触发
        if (null != cronScheduleBuilder) {
            triggerBuilder.withSchedule(cronScheduleBuilder);
            scheduler.scheduleJob(jobDetail, triggerBuilder.build());
        }else {
            scheduler.addJob(jobDetail, true);
        }

    }

    @Override
    public List<QuartzEntity> list(BaseQuery<QuartzEntity> query) throws SchedulerException {
        QuartzEntity entity = query.getQuery();
        List<QuartzEntity> list = quartzEntityMapper.list(entity);

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        for (QuartzEntity quartzEntity : list) {
            JobKey key = new JobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(key);
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            if (null == jobDataMap) {
                continue;
            }
            quartzEntity.setConfigData(new HashMap<>(16));
            for (String dataKey : jobDataMap.getKeys()) {
                quartzEntity.getConfigData().put(dataKey, jobDataMap.getString(dataKey));
            }
        }

        return list;
    }

    @Override
    public void start(QuartzEntity entity) throws SchedulerException {
        JobKey key = new JobKey(entity.getJobName(), entity.getJobGroup());
        scheduler.triggerJob(key);
    }

    @Override
    public void stop(QuartzEntity entity) throws UnableToInterruptJobException {
        JobKey key = new JobKey(entity.getJobName(), entity.getJobGroup());
        scheduler.interrupt(key);
    }

    @Override
    public void remove(QuartzEntity entity) throws SchedulerException {
        QuartzEntity query = new QuartzEntity();
        query.setJobName(entity.getJobName());
        query.setJobGroup(entity.getJobGroup());
        List<QuartzEntity> quartzEntities = quartzEntityMapper.list(query);
        if (CollectionUtils.isEmpty(quartzEntities)) {
            return;
        }

        for (QuartzEntity item : quartzEntities) {
            if(StringUtils.isNotBlank(item.getTriggerName())) {
                TriggerKey triggerKey = TriggerKey.triggerKey(item.getTriggerName());
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
            }
            this.stop(item);
            scheduler.deleteJob(JobKey.jobKey(entity.getJobName(), entity.getJobGroup()));
        }
    }

    @Override
    public void update(QuartzEntity entity) throws Exception {
        remove(entity);
        add(entity);
    }
}
