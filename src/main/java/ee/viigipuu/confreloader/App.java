package ee.viigipuu.confreloader;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.viigipuu.confreloader.config.ConfigChangeListener;
import ee.viigipuu.confreloader.config.ConfigOps;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOG.info("Starting ...");
        new App().run(args);
    }

    public void run(String[] args) {
        LOG.debug("running...");
        for (String s : args) {
            LOG.debug("arg: {}", s);
        }
        Path confPath = ConfigOps.getConfPath(args);
        ConfigChangeListener confChangeListener = new ConfigChangeListener(confPath);
        new Thread(confChangeListener).start();
    }
}
