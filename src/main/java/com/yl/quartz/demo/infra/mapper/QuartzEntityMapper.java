package com.yl.quartz.demo.infra.mapper;

import com.yl.quartz.demo.domain.entity.QuartzEntity;

import java.util.List;

/**
 * @author li.yang01@hand-china.com 2021/1/15 2:33 下午
 */
public interface QuartzEntityMapper {

    /**
     * 任务列表
     * @param entity
     * @return
     */
    List<QuartzEntity> list(QuartzEntity entity);

}
