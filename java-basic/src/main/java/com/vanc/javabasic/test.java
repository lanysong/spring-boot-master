package com.vanc.javabasic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-08-06 17:52
 */
public class test {
	private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void main(String[] args) throws ParseException {
		
		Date date1 = simpleDateFormat.parse("2019-10-01 10:11:23");
		Date date2 = simpleDateFormat.parse("2019-10-02 10:11:23");
		
		System.out.println(date1.after(date2));
	}
}
