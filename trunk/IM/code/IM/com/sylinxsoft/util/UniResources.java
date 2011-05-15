package com.sylinxsoft.util;

import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 * 为了支持多国语言和国际化，采用资源束类来统一管理<br>
 * 注意在程序中用到的字符串资源请都保存到对应的资源文件中,<br>
 * 然后通过UniResources．getString(key)取得所要的字符串,<br>
 * 这样只要编写不同的资源文件就能实现不同的语言字符串<br>
 * 
 * @author yqg
 *
 */
public class UniResources {

	/**支持简体中文的资源文件*/
	public static final String CHINABOUNDLE = "ui.resources.UniResources_zh_CN";
	/**支持英语的资源文件*/
	public static final String ENGLISHBOUNDLE = "ui.resources.UniResources_en_CN";
	/**支持繁体中文的资源文件*/
	public static final String TAIWANBOUNDLE = "ui.resources.UniResources_zh_TW";

	/**
	 * 为了支持多国语言和国际化，这里使用资源束,其中资源文件保存在ui/resources/**.properties，
	 * 注意这里的资源文件一定要是.properties的扩展名,默认是中文
	 */
	private static ResourceBundle BOUNDLE = ResourceBundle
			.getBundle(CHINABOUNDLE);

	/**
	 * 根据所要查找的键获得其值，这里的键与值保存在资源文件，<br>
	 * 其.properties资源文件书写规范请参考相关资料．<br>
	 * @param key
	 * @return 键所对应的值
	 */
	public static String getString(String key) {
		try {
			return BOUNDLE.getString(key);
		} catch (MissingResourceException e) {
			System.out.println("读写资源文件出错!----" + key);
			return null;
		}
	}

	public static void setUniResources(String str) {
		BOUNDLE = ResourceBundle.getBundle(str);
	}

}
