package tools.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Common configurations informations.
 *
 * @author A. CANDIAN
 *
 */
public abstract class AbstractConfiguration {

    private static final Logger LOGGER = Logger.getLogger(AbstractConfiguration.class.getName());

    /**
     * Key to get the proxy type to use.
     */
    public static final String PROXY_TYPE = "proxy.type";
    /**
     * Key to get the proxy host to use.
     */
    public static final String PROXY_HOST = "proxy.host";
    /**
     * Key to get the proxy port to use.
     */
    public static final String PROXY_PORT = "proxy.port";

    /**
     * The actual configurations.
     */
    protected Properties properties;

    /**
     * Try to load a "configs.properties" file at the root of the application.
     */
    protected AbstractConfiguration() {

        properties = new Properties();

        try (FileInputStream in = new FileInputStream("./configs.properties")) {
            properties.load(in);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Problème à l'ouverture du fichier de configuration.", e);
        }
    }

    /**
     * Build a {@link Proxy} object using the configurations.<br>
     * The proxy type can be SOCKS, or HTTP, any other value will just result as
     * NO_PROXY. <br>
     * Can throw a {@link NumberFormatException} if the proxy port is not a
     * valid integer.
     *
     * @return A {@link Proxy} object.
     */
    public Proxy getProxy() {
        Proxy retour = Proxy.NO_PROXY;

        String proxyType = properties.getProperty(PROXY_TYPE);
        String proxyHost = properties.getProperty(PROXY_HOST);
        String proxyPort = properties.getProperty(PROXY_PORT);

        // If the 3 params are filled
        if (!(proxyType == null || proxyHost == null || proxyPort == null)) {
            int port = Integer.parseInt(proxyPort, 10);
            if ("HTTP".equalsIgnoreCase(proxyType)) {
                retour = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, port));
            } else if ("SOCKS".equalsIgnoreCase(proxyType)) {
                retour = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyHost, port));
            }
        }

        return retour;
    }
}
