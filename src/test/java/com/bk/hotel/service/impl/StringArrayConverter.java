package com.bk.hotel.service.impl;

public class StringArrayConverter {


	public static String[] convert(Object arg) {
        return ((String) arg).split("\\s*,\\s*");
	}
}
