package com.hd.hse.dc.business.web.zyxk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ParamsName {
    /**
     * @Method: value()
     * @author create by Tang
     * @date date 16/8/23 下午3:55
     * @Description:
     * 返回Map的key Name,默认为参数名
     */
    String value();
}