package com.yl.quartz.demo.infra.constants;

/**
 * 常量
 *
 * @author li.yang01@hand-china.com 2021/1/15 3:02 下午
 */
public interface BaseConstants {

    String JOB_TAG = "Job Manager";

    /**
     * 响应状态
     */
    interface Status {
        /**
         * 成功
         */
        String SUCCESS = "success";
        /**
         * 失败
         */
        String FAILED = "failed";
    }

    /**
     * 日期时间匹配格式
     */
    interface Pattern {
        //
        // 常规模式
        // ----------------------------------------------------------------------------------------------------
        /**
         * yyyy-MM-dd
         */
        String DATE = "yyyy-MM-dd";
        /**
         * yyyy-MM-dd HH:mm:ss
         */
        String DATETIME = "yyyy-MM-dd HH:mm:ss";
        /**
         * yyyy-MM-dd HH:mm
         */
        String DATETIME_MM = "yyyy-MM-dd HH:mm";
        /**
         * yyyy-MM-dd HH:mm:ss.SSS
         */
        String DATETIME_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
        /**
         * HH:mm
         */
        String TIME = "HH:mm";
        /**
         * HH:mm:ss
         */
        String TIME_SS = "HH:mm:ss";

        //
        // 系统时间格式
        // ----------------------------------------------------------------------------------------------------
        /**
         * yyyy/MM/dd
         */
        String SYS_DATE = "yyyy/MM/dd";
        /**
         * yyyy/MM/dd HH:mm:ss
         */
        String SYS_DATETIME = "yyyy/MM/dd HH:mm:ss";
        /**
         * yyyy/MM/dd HH:mm
         */
        String SYS_DATETIME_MM = "yyyy/MM/dd HH:mm";
        /**
         * yyyy/MM/dd HH:mm:ss.SSS
         */
        String SYS_DATETIME_SSS = "yyyy/MM/dd HH:mm:ss.SSS";

        //
        // 无连接符模式
        // ----------------------------------------------------------------------------------------------------
        /**
         * yyyyMMdd
         */
        String NONE_DATE = "yyyyMMdd";
        /**
         * yyyyMMddHHmmss
         */
        String NONE_DATETIME = "yyyyMMddHHmmss";
        /**
         * yyyyMMddHHmm
         */
        String NONE_DATETIME_MM = "yyyyMMddHHmm";
        /**
         * yyyyMMddHHmmssSSS
         */
        String NONE_DATETIME_SSS = "yyyyMMddHHmmssSSS";

        /**
         * EEE MMM dd HH:mm:ss 'CST' yyyy
         */
        String CST_DATETIME = "EEE MMM dd HH:mm:ss 'CST' yyyy";

        //
        // 数字格式
        // ------------------------------------------------------------------------------
        /**
         * 无小数位 0
         */
        String NONE_DECIMAL = "0";
        /**
         * 一位小数 0.0
         */
        String ONE_DECIMAL = "0.0";
        /**
         * 两位小数 0.00
         */
        String TWO_DECIMAL = "0.00";
        /**
         * 千分位表示 无小数 #,##0
         */
        String TB_NONE_DECIMAL = "#,##0";
        /**
         * 千分位表示 一位小数 #,##0.0
         */
        String TB_ONE_DECIMAL = "#,##0.0";
        /**
         * 千分位表示 两位小数 #,##0.00
         */
        String TB_TWO_DECIMAL = "#,##0.00";

    }

}
