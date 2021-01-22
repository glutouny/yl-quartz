package com.yl.quartz.demo.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author li.yang01@hand-china.com 2021/1/15 11:49 上午
 */
@Data
@ApiModel("JOB任务")
public class QuartzEntity {

    @NotBlank
    @ApiModelProperty("任务名称")
    private String jobName;

    @NotBlank
    @ApiModelProperty("任务分组")
    private String jobGroup;

    @ApiModelProperty("任务分组")
    private String description;

    @NotBlank
    @ApiModelProperty("执行类")
    private String jobClassName;

    @NotBlank
    @ApiModelProperty("执行计划")
    private String cronExpression;

    @ApiModelProperty("任务执行名称")
    private String triggerName;

    @ApiModelProperty("任务状态")
    private String triggerState;

    @ApiModelProperty("任务名称 用于修改")
    private String oldJobName;

    @ApiModelProperty("任务分组 用于修改")
    private String oldJobGroup;

    @ApiModelProperty("任务参数")
    private Map<String, String> configData;

}
