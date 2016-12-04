package com.khzpds.util;

import java.util.List;


public class ArrayUtils extends org.apache.commons.lang.ArrayUtils{

	/**
	 * 集合为空
	 * @param list
	 * @return
	 */
	public static boolean isNull(List<?> list) {
		return list == null || list.isEmpty() || list.size() == 0;
	}
	
	/**
	 * 集合非空
	 * @param list
	 * @return
	 */
	public static boolean isNotNull(List<?> list) {
		return list != null && list.size() > 0;
	}
}
