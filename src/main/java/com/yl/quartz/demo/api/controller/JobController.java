package com.yl.quartz.demo.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yl.quartz.demo.app.service.JobService;
import com.yl.quartz.demo.domain.base.BaseQuery;
import com.yl.quartz.demo.domain.base.BaseResponse;
import com.yl.quartz.demo.domain.entity.QuartzEntity;
import com.yl.quartz.demo.infra.constants.BaseConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * JOB 管理API
 *
 * @author li.yang01@hand-china.com 2021/1/15 3:00 下午
 */
@Api(tags = BaseConstants.JOB_TAG)
@RestController
@RequestMapping("/job")
public class JobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @ApiOperation("任务列表")
    @PostMapping("/list")
    public ResponseEntity<BaseResponse<PageInfo<QuartzEntity>>> list(@RequestBody BaseQuery<QuartzEntity> query) {
        BaseResponse<PageInfo<QuartzEntity>> response = BaseResponse.ok();
        if (null == query.getQuery()) {
            query.setQuery(new QuartzEntity());
        }
        PageHelper.startPage(query.getPage(), query.getPageSize());
        List<QuartzEntity> result;
        try {
            result = jobService.list(query);
        } catch (SchedulerException e) {
            LOGGER.error("query job list error",e);
            response = BaseResponse.error(e.getMessage());
            result = Collections.emptyList();
        }
        PageInfo<QuartzEntity> pageInfo = new PageInfo<>(result);
        response.setResult(pageInfo);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("添加任务")
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<String>> add(@RequestBody QuartzEntity entity) {
        BaseResponse<String> response = BaseResponse.ok();
        try {
            jobService.add(entity);
        } catch (Exception e) {
            LOGGER.error("add job error", e);
            response = BaseResponse.error(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @ApiOperation("启用任务")
    @PostMapping("/start")
    public ResponseEntity<BaseResponse<String>> start(@RequestBody QuartzEntity entity) {
        BaseResponse<String> response = BaseResponse.ok();
        try {
            jobService.start(entity);
        } catch (Exception e) {
            LOGGER.error("start job error", e);
            response = BaseResponse.error(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @ApiOperation("停止任务")
    @PostMapping("/stop")
    public ResponseEntity<BaseResponse<String>> stop(@RequestBody QuartzEntity entity) {
        BaseResponse<String> response = BaseResponse.ok();
        try {
            jobService.stop(entity);
        } catch (Exception e) {
            LOGGER.error("stop job error:", e);
            response = BaseResponse.error(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @ApiOperation("删除任务")
    @PostMapping("/remove")
    public ResponseEntity<BaseResponse<String>> remove(@RequestBody QuartzEntity entity) {
        BaseResponse<String> response = BaseResponse.ok();
        try {
            jobService.remove(entity);
        } catch (Exception e) {
            LOGGER.error("remove job error:", e);
            response = BaseResponse.error(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @ApiOperation("更新任务")
    @PostMapping("/update")
    public ResponseEntity<BaseResponse<String>> update(@RequestBody QuartzEntity entity) {
        BaseResponse<String> response = BaseResponse.ok();
        try {
            jobService.update(entity);
        } catch (Exception e) {
            LOGGER.error("update job error:", e);
            response = BaseResponse.error(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @ApiOperation("批量添加任务")
    @PostMapping("/batch-add")
    public ResponseEntity<BaseResponse<String>> batchAdd(@RequestBody List<QuartzEntity> entityList) {
        BaseResponse<String> response = BaseResponse.ok();
        try {
            for(QuartzEntity entity : entityList) {
                jobService.add(entity);
            }
        } catch (Exception e) {
            LOGGER.error("batch add job error:", e);
            response = BaseResponse.error(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
