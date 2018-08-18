package com.bk.hotel.test.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.params.aggregator.AggregateWith;

import com.bk.hotel.service.impl.DateValidationBeanAggregator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@AggregateWith(DateValidationBeanAggregator.class)
public @interface ToDataValidationBean {

}