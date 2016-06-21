package com.meijia.utils;

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 配置文件初始化类
 *
 */
public final class ConfigPropertiesUtil extends Properties {
	public static ConfigPropertiesUtil instance;
	public ResourceBundle rb;

	private ConfigPropertiesUtil() {
		this.rb = ResourceBundle.getBundle("config");
	}

	// 保持对象同步
	private static synchronized void makeInstance() {
		if (instance == null) {
			instance = new ConfigPropertiesUtil();
		}
	}

	public static ConfigPropertiesUtil getInstance() {
		if (instance != null) {
			return instance;
		} else {
			makeInstance();
			return instance;
		}
	}

	public ResourceBundle getRb() {
		return rb;
	}

	public void setRb(ResourceBundle rb) {
		this.rb = rb;
	}

	public static void setInstance(ConfigPropertiesUtil instance) {
		ConfigPropertiesUtil.instance = instance;
	}
	
	public static String getKey(String key) {
		return ConfigPropertiesUtil.getInstance().getRb().getString(key);
	}
}
