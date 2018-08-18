package com.bk.hotel.test.utils;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@Target({ TYPE, METHOD })
@Retention(RetentionPolicy.RUNTIME)
@EnabledOnOs({OS.LINUX, OS.MAC})
public @interface EnabledOnNixSystems {

}
