package com.yl.quartz.demo.domain.base;

import lombok.Data;

/**
 * @author li.yang01@hand-china.com 2021/1/15 11:31 上午
 */
@Data
public class BaseQuery<T> {

    private T query;

    private Integer page = 1;

    private Integer pageSize = 10;
}
