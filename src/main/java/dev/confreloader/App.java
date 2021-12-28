package dev.confreloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOG.info("Starting ...");
        new App().run();
    }

    public void run() {
       LOG.debug("running...");     
    }
}
