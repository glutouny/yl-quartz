package com.yl.quartz.demo.domain.base;

import com.yl.quartz.demo.infra.constants.BaseConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author li.yang01@hand-china.com 2021/1/15 11:31 上午
 */
@NoArgsConstructor
@Data
public class BaseResponse<T> {

    private T result;

    private Long total;

    private String statue;

    private String msg;

    public static BaseResponse ok() {
        BaseResponse response = new BaseResponse<>();
        response.setStatue(BaseConstants.Status.SUCCESS);
        return response;
    }

    public static   BaseResponse error(String errorMsg) {
        BaseResponse response = new BaseResponse<>();
        response.setStatue(BaseConstants.Status.FAILED);
        response.setMsg(errorMsg);
        return response;
    }

}

