package com.sylinxsoft.util;

import java.util.Comparator;



/**
 * 字符串比较大小
 * @author Administrator
 *
 */
public class StringComparator implements Comparator {
	public int compare(Object arg0, Object arg1) {
		String task1 = (String) arg0;
		String task2 = (String) arg1;
		return task1.compareTo(task2);
	}
}
