package dev.confreloader.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration loader/holder
 */
public class AppConfig {

	public static final String CONF_ENV_VAR = "APP_CONF";
	public static final String DEFAULT_CONF_LOCATION = "src/main/resources/config.properties";

	private static final AppConfig INSTANCE = new AppConfig();
	private static final Properties CONFIGURATION = new Properties();
	private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

	// Singleton
	private AppConfig() {
	}

	public static final AppConfig getInstance() {
		return INSTANCE;
	}

	private static final Properties getConfiguration() {
		return CONFIGURATION;
	}

	/**
	 * App conf setup
	 * 
	 * @param filePath - configuration file path
	 */
	public void initialize(final Path filePath) {

		InputStream inStream = null;
		try {
			inStream = new FileInputStream(filePath.toFile());
			CONFIGURATION.load(inStream);
		} catch (IOException ioe) {
			LOG.error(ioe.toString());
		}
	}

	public String getValue(final String key) {
		return (String) getConfiguration().getProperty(key);
	}
}
