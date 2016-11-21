package com.khzpds.base;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * app variant configuration
 * @author Vincent
 *
 */
public abstract class SystemConfig {
	private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);
	
	static String mailHost = null;
	static String mailAccount = null;
	static String mailName = null;
	static String mailPassword = null;
	// configuration file location
	private static String DEFAULT_CONFIG_FILE = "config.properties";

	// configuration holder, sub classes could fill more configuration
	public static CombinedConfiguration config;
	
	static {
		config = new CombinedConfiguration();
		try {
			PropertiesConfiguration plain = new PropertiesConfiguration(DEFAULT_CONFIG_FILE);
			plain.setReloadingStrategy(new FileChangedReloadingStrategy());
			logger.info(String.format("{%s}配置文件加载成功", DEFAULT_CONFIG_FILE));
			config.append(plain);
		} catch (ConfigurationException e) {
			logger.error(String.format("配置文件{%s}加载失败!", DEFAULT_CONFIG_FILE),e);
		}
	}

	// mail
	public static String getMailHost(){
		if(mailHost == null)
			mailHost = config.getString("mail.host");
		return mailHost;
	}
	
	public static String getMailAccount(){
		if(mailAccount == null)
			mailAccount = config.getString("mail.account");
		return mailAccount;
	}
	
	public static String getMailName(){
		if(mailName == null)
			mailName = config.getString("mail.name");
		return mailName;
	}
	
	public static String getMailPassword(){
		if(mailPassword == null)
			mailPassword = config.getString("mail.password");
		return mailPassword;
	}
}
