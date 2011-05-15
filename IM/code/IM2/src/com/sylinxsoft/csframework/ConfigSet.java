package com.sylinxsoft.csframework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 用属性文件存取配置信息
 * 
 * @author Administrator
 * 
 */
public class ConfigSet {

	private String filePath = null;
	private Properties propSet = null;
	private static ConfigSet configSet;

	/**
	 * 配置文件所在路径
	 * 
	 * @param file
	 */
	private ConfigSet(String file) {
		this.filePath = file;
		configSet = this;
	}

	public static ConfigSet getInstance() {
		if (null == configSet) {
			String configFilePath = System.getProperty("user.home")
					+ File.separator + "mskConfig.properties";
			configSet = new ConfigSet(configFilePath);
			configSet.refreshSetting();
		}
		return configSet;
	}

	/**
	 * 保存配置信息
	 * 
	 * @param prop
	 */
	public void saveSetting(Properties prop) {

		if (null == prop) {
			return;
		}
		Properties config;
		FileOutputStream fo;
		File ff = new File(filePath);
		try {
			if (!ff.exists()) {
				ff.createNewFile();
			}
			fo = new FileOutputStream(ff);
			config = prop;
			propSet = prop;
			config.store(fo, "SylinxSoft Msk Configuration File");
			fo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得配置信息
	 * 
	 * @return
	 */
	public Properties refreshSetting() {

		Properties config = new Properties();
		File file = new File(filePath);
		// 判断文件是否存在
		if (file.exists()) {
			FileInputStream fi;
			try {
				fi = new FileInputStream(filePath);
				config.load(fi);
				fi.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		propSet = config;
		return config;

	}

	/**
	 * 从属性文件中获得对应的键值
	 * 
	 * @param propSetName
	 * @return
	 */
	public String getProperty(String propSetName) {
		if (null != propSet) {
			return propSet.getProperty(propSetName);
		}
		return null;
	}

	public static void main(String[] args) {

		System.out.println(System.getProperty("user.home"));

	}

}
