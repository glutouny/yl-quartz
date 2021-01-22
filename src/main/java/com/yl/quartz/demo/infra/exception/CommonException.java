package com.yl.quartz.demo.infra.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 封装运行异常为CommonException
 *
 * @author li.yang01@hand-china.com 2021/1/15 2:04 下午
 */
public class CommonException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(CommonException.class);

    private final transient Object[] parameters;

    private String code;

    /**
     * 构造器
     *
     * @param code       异常code
     * @param parameters parameters
     */
    public CommonException(String code, Object... parameters) {
        super(code);
        this.parameters = parameters;
        this.code = code;
    }

    public CommonException(String code, Throwable cause, Object... parameters) {
        super(code, cause);
        this.parameters = parameters;
        this.code = code;
    }

    public CommonException(String code, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.parameters = new Object[]{};
    }


    public CommonException(Throwable cause, Object... parameters) {
        super(cause);
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public String getCode() {
        return code;
    }

    public String getTrace() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps;
        try {
            ps = new PrintStream(baos, false, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            logger.error("Error get trace, unsupported encoding.", e);
            return null;
        }
        this.printStackTrace(ps);
        ps.flush();
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("code", code);
        map.put("message", super.getMessage());
        return map;
    }

}

