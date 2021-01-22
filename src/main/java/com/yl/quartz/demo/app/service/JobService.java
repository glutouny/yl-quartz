package com.yl.quartz.demo.app.service;

import com.yl.quartz.demo.domain.base.BaseQuery;
import com.yl.quartz.demo.domain.entity.QuartzEntity;
import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;

import java.util.List;

/**
 * JOB处理服务
 *
 * @author li.yang01@hand-china.com 2021/1/15 11:29 上午
 */
public interface JobService {

    /**
     * 添加任务
     * @param entity job任务参数
     * @throws Exception 异常
     */
    void add(QuartzEntity entity) throws Exception;

    /**
     * 任务列表
     * @param query
     * @return
     * @throws SchedulerException 异常
     */
    List<QuartzEntity> list(BaseQuery<QuartzEntity> query) throws SchedulerException;

    /**
     * 启动任务
     * @param entity  job任务参数
     * @throws SchedulerException  异常
     */
    void start(QuartzEntity entity) throws SchedulerException;

    /**
     * 停止任务
     * @param entity
     * @throws UnableToInterruptJobException
     */
    void stop(QuartzEntity entity) throws UnableToInterruptJobException;


    /**
     * 删除任务
     * @param entity  job任务参数
     * @throws SchedulerException 异常
     */
    void remove(QuartzEntity entity) throws SchedulerException;

    /**
     * 更新任务
     * @param entity job任务参数
     * @throws Exception 异常
     */
    void update(QuartzEntity entity) throws Exception;
}
