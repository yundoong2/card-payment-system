package com.yoonhyeok.cardPayment.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 유효성 검증 대상을 지정하기 위한 Annotation
 * @author cyh68
 * @since 2023-03-23
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validation {

}
