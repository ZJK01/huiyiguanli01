package com.example.demo.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Timeformat {
		
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.YEAR, year);
		cal1.set(Calendar.MONTH, month);
		cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal1.getTime());
	}

	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, year);
		cal2.set(Calendar.MONTH, month);
		cal2.set(Calendar.DAY_OF_MONTH,1);			//设置为1号,当前日期既为本月第一天
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal2.getTime());
	}
}
