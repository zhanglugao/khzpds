package com.khzpds.util;

import java.util.UUID;
 
public class UUIDUtil {
	
	/**
	 * 获取UUID
	 * @return 字符串
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
	}
	
}
