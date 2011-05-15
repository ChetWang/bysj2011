/**
 * 
 */
package com.sylinxsoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Administrator
 * 
 */
public class DateFormater {

	/**
	 * ���ַ���ת�������� 
	 * 
	 * @param datestr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String datestr, String pattern)
			throws ParseException {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		d = sdf.parse(datestr);
		return d;
	}

	/**
	 * ������ת�����ַ���
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		Calendar temp = Calendar.getInstance(TimeZone.getDefault());
		temp.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(temp.getTime());
	}
}
