package com.sylinxsoft.util;

import java.util.StringTokenizer;

public class IpTools {

	public static byte[] getIpByString(String strIp) {

		byte[] attr = new byte[4];

		StringTokenizer token = new StringTokenizer(strIp, ".");
		int index = 0;
		while (token.hasMoreTokens()) {
			try {
			   attr[index] = Byte.parseByte(token.nextToken());
			   index++;
			} catch (Exception e){
				System.out.println(e);
				return null;
			}
		}
		return attr;

	}
}
