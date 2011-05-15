package com.sylinxsoft.util;

import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 * Ϊ��֧�ֶ�����Ժ͹��ʻ���������Դ������ͳһ����<br>
 * ע���ڳ������õ����ַ�����Դ�붼���浽��Ӧ����Դ�ļ���,<br>
 * Ȼ��ͨ��UniResources��getString(key)ȡ����Ҫ���ַ���,<br>
 * ����ֻҪ��д��ͬ����Դ�ļ�����ʵ�ֲ�ͬ�������ַ���<br>
 * 
 * @author yqg
 *
 */
public class UniResources {

	/**֧�ּ������ĵ���Դ�ļ�*/
	public static final String CHINABOUNDLE = "ui.resources.UniResources_zh_CN";
	/**֧��Ӣ�����Դ�ļ�*/
	public static final String ENGLISHBOUNDLE = "ui.resources.UniResources_en_CN";
	/**֧�ַ������ĵ���Դ�ļ�*/
	public static final String TAIWANBOUNDLE = "ui.resources.UniResources_zh_TW";

	/**
	 * Ϊ��֧�ֶ�����Ժ͹��ʻ�������ʹ����Դ��,������Դ�ļ�������ui/resources/**.properties��
	 * ע���������Դ�ļ�һ��Ҫ��.properties����չ��,Ĭ��������
	 */
	private static ResourceBundle BOUNDLE = ResourceBundle
			.getBundle(CHINABOUNDLE);

	/**
	 * ������Ҫ���ҵļ������ֵ������ļ���ֵ��������Դ�ļ���<br>
	 * ��.properties��Դ�ļ���д�淶��ο�������ϣ�<br>
	 * @param key
	 * @return ������Ӧ��ֵ
	 */
	public static String getString(String key) {
		try {
			return BOUNDLE.getString(key);
		} catch (MissingResourceException e) {
			System.out.println("��д��Դ�ļ�����!----" + key);
			return null;
		}
	}

	public static void setUniResources(String str) {
		BOUNDLE = ResourceBundle.getBundle(str);
	}

}
