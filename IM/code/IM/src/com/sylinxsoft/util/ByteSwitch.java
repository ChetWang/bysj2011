package com.sylinxsoft.util;

/**
 * 各种数据类型转化为byte数组
 * <ul>
 * <li>boolean   1   位</li>
 * <li>char      16  位</li>
 * <li>byte      8   位</li>
 * <li>short     16  位</li>
 * <li>int       32  位  </li>
 * <li>long      64  位</li>
 * <li>float     32  位</li>
 * <li>double    64  位</li>
 * </ul>
 * @author yqg
 *
 */
public class ByteSwitch {

	public static byte[] shortToBytes(short s) {
		byte[] buf = new byte[2];
		int pos;
		for (pos = 0; pos < 2; pos++) {
			buf[pos] = (byte) (s & 0xff);
			s >>= 8;	
		}
		return buf;
	}
	


	
	
	public static void main(String[] args) {
		//short sht = 1201;
		//byte[] buf = ByteSwitch.shortToBytes(sht);
		/*System.out.println(Byte.MAX_VALUE);
		int max = 80;
		String str ="-commond" + "%#" + Short.MAX_VALUE +  "%#"
		        + Long.MAX_VALUE +  "%#" + 145858 +  "%#" + Long.MAX_VALUE +  "%#";
		
		for(int i = str.length() ; i < max;i ++) {
			str=str+"0";
		}
		
		System.out.println("length:" + str.length());
		System.out.println(str);
		byte[] maxBytes = str.getBytes();
		System.out.println(maxBytes.length);
		String str2 = new String(maxBytes);
		System.out.println(str2);*/
		
		byte[] bs ={'b','b','b','b','b','b','b','b'};// "commandd".getBytes();
		System.out.println(new String(bs));
		
		
		
	}

}
