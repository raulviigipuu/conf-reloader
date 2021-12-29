package dev.confreloader.config;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigChangeListener implements Runnable {

	private String configFileName = null;
	private Path filePath = null;

	private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

	public ConfigChangeListener(Path filePath) {

		this.filePath = filePath;
	}

	@Override
	public void run() {

		register(this.filePath);
	}

	private void register(Path filePath) {

		this.configFileName = filePath.getFileName().toString();

		confChanged(filePath);
		try {
			startWatcher(filePath);
		} catch (IOException ioe) {
			LOG.error(ioe.toString());
		}
	}

	private void startWatcher(Path filePath) throws IOException {

		WatchService watchService = FileSystems.getDefault().newWatchService();
		filePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

		WatchKey watchKey = null;
		while (true) {

			try {
				watchKey = watchService.take();
				for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
					if (watchEvent.context().toString().equals(configFileName)) {
						confChanged(filePath);
					}
				}

				boolean reset = watchKey.reset();
				if (!reset) {
					LOG.warn("Could not reset the watch key!");
					break;
				}
			} catch (InterruptedException ie) {
				LOG.error(ie.toString());
			}
		}
		if (watchService != null) {
			watchService.close();
		}
	}

	public void confChanged(Path filePath) {

		LOG.info("Refreshing the configuration.");
		AppConfig.getInstance().initialize(filePath);
		LOG.debug("prop-one: {}", AppConfig.getInstance().getValue("prop-one"));
		LOG.debug("prop-two: {}", AppConfig.getInstance().getValue("prop-two"));
	}
}
