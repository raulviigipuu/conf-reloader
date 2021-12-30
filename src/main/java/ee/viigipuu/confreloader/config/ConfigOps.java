package ee.viigipuu.confreloader.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Config helper functions
 */
public class ConfigOps {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigOps.class);

    /**
     * Searches conf file
     *
     * @param args - main args array
     * @return - Path to conf location
     */
    public static Path getConfPath(String[] args) {

        Path confPath = null;

        // First let's check the environment
        String confEnv = System.getenv(AppConfig.CONF_ENV_VAR);
        if (confEnv != null && !confEnv.isEmpty()) {
            confPath = Paths.get(confEnv);
            LOG.debug("Conf found in environment.");
        }

        if (confPath == null || !Files.isRegularFile(confPath)) {
            // Secondly we look if conf path is specified in command line
            Map<String, String> paramMap = parseArgs(args);
            if (paramMap.containsKey("conf")) {
                confPath = Paths.get(paramMap.get("conf"));
                LOG.debug("Conf specified on command line.");
            }
            // Use default conf location
            if (confPath == null || !Files.isRegularFile(confPath)) {
                confPath = Paths.get(AppConfig.DEFAULT_CONF_LOCATION);
                LOG.debug("Using default conf.");
            }
        }

        LOG.info("Using conf from: " + confPath.toString());

        return confPath;
    }

    /**
     * Parses command line arguments
     *
     * @param args - main args array
     * @return - Map of recognized conf parameters
     */
    public static Map<String, String> parseArgs(String[] args) {

        Map<String, String> paramMap = new HashMap<String, String>();

        for (String strArg : args) {

            if (strArg.startsWith("--conf=")) {
                paramMap.put("conf", strArg.substring(7));
            }
        }

        return paramMap;
    }
}
