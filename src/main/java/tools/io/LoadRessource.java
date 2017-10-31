package tools.io;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

/**
 * Contain methods to work on streams.
 * 
 * @author A. CANDIAN
 *
 */
public final class LoadRessource {

    private static final Logger LOGGER = Logger.getLogger(LoadRessource.class.getName());

    /**
     * Default buffer size.
     */
    private static final int DEFAULT_BUFFER_SIZE = 102400;

    /**
     * Default timeout.
     */
    public static final int DEFAULT_TIMEOUT = 10000;

    /**
     * Default number of tries to open streams.
     */
    public static final int DEFAULT_ATTEMPTS = 5;

    /**
     * Timeout used by network operations. If not configured, use
     * {@link LoadRessource#DEFAULT_TIMEOUT}.
     */
    private static int timeout = DEFAULT_TIMEOUT;

    /**
     * Default number of tries to open streams. If not configured, use
     * {@link LoadRessource#DEFAULT_ATTEMPTS}.
     */
    private static int attempts = DEFAULT_ATTEMPTS;

    /**
     * Proxy used when using URL connection.<br>
     * Default is {@link Proxy#NO_PROXY}.
     */
    private static Proxy proxy = Proxy.NO_PROXY;

    /**
     * Properties that will be used as HTTP headers when using URL connection.
     */
    private static Properties properties = null;

    private LoadRessource() {

    }

    /**
     * Set the proxy to be used when using URL connection.
     * 
     * @param proxy
     *            The proxy to use.
     */
    public static void setProxy(Proxy proxy) {
        LoadRessource.proxy = proxy;
    }

    /**
     * Set the attempt number before throwing an exception.
     * 
     * 
     * @param tries
     *            New attempt number before throwing an exception.
     */
    public static void setAttempts(int tries) {
        LoadRessource.attempts = tries;
    }

    /**
     * Change the timeout used when opening resources.
     * 
     * @param timeout
     *            The new timeout in microseconds.
     */
    public static void setTimeout(int timeout) {
        LoadRessource.timeout = timeout;
    }

    /**
     * Set the potentials HTTP headers to send when using URL to open an http
     * stream.
     * 
     * @param properties
     *            A {@link Properties} object containing the HTTP headers to
     *            use.
     */
    public static void setProperties(Properties properties) {
        LoadRessource.properties = properties;
    }

    /**
     * Get the current properties.
     * 
     * @return A {@link Properties} object.
     */
    public static Properties getProperties() {
        return properties;
    }

    /**
     * Do a request on the urlRessource parameter after passing it to
     * {@link URL}, and get the result as a byte array.
     * 
     * See also {@link LoadRessource#getRessource(URL, OutputStream, int, int)
     * getRessource}.
     * 
     * @param urlRessource
     *            URL formatted string of the resource to load.
     * @return Binary data of the loaded object.
     * @throws IOException
     *             If an error happen while loading data.
     */
    public static byte[] getRessource(String urlRessource) throws IOException {
        return getRessource(new URL(urlRessource));
    }

    /**
     * Do a request on the urlRessource parameter, and get the result as a byte
     * array.
     * 
     * See also {@link LoadRessource#getRessource(URL, OutputStream, int, int)
     * getRessource}.
     * 
     * @param urlRessource
     *            URL of the resource to load.
     * @return Binary data of the loaded object.
     * @throws IOException
     *             If an error happen while loading data.
     */
    public static byte[] getRessource(URL urlRessource) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        getRessource(urlRessource, out);
        return out.toByteArray();
    }

    /**
     * Do a request on the urlRessource parameter, and write the result to
     * another stream.
     * 
     * See also {@link LoadRessource#getRessource(URL, OutputStream, int, int)
     * getRessource}.
     * 
     * @param urlRessource
     *            URL of the resource to load.
     * @param out
     *            The stream to write loaded data.
     * 
     * @throws IOException
     *             If an error happen while loading data.
     */
    public static void getRessource(String urlRessource, OutputStream out) throws IOException {
        getRessource(new URL(urlRessource), out);
    }

    /**
     * Do a request on the urlRessource parameter, and write the result to a
     * local file.
     * 
     * See also {@link LoadRessource#getRessource(URL, OutputStream, int, int)
     * getRessource}.
     * 
     * @param urlOrigine
     *            URL string of the resource to load.
     * @param outputFile
     *            The file to write loaded data.
     * 
     * @throws IOException
     *             If an error happen while loading data.
     */
    public static void saveFile(String urlOrigine, String outputFile) throws IOException {
        getRessource(urlOrigine, new FileOutputStream(outputFile));
    }

    /**
     * Same as <code>getRessource(urlRessource, out, timeout, attempts)</code>.
     * 
     * @param urlRessource
     *            The URL to load.
     * @param out
     *            The stream to write the loaded data.
     * @throws IOException
     *             If an error happen while loading data, if there is more than
     *             <code>attempts</code> timeouts.
     */
    public static void getRessource(URL urlRessource, OutputStream out) throws IOException {
        getRessource(urlRessource, out, timeout, attempts);
    }

    /**
     * Same as {@link LoadRessource#getRessource(URL, OutputStream, int, int)
     * getRessource(new URL(urlRessource), out, timeOut, attempts)}
     * 
     * @param urlRessource
     *            The URL string of the resource to load.
     * @param out
     *            The stream to write the request result.
     * @param timeOut
     *            Timeout to use when loading resource.
     * @param attempts
     *            Attempts number before throwing an exception.
     * @throws IOException
     *             If an error happen while loading data, if there is more than
     *             <code>attempts</code> timeouts.
     */
    public static void getRessource(String urlRessource, OutputStream out, int timeOut, int attempts)
            throws IOException {
        getRessource(new URL(urlRessource), out, timeOut, attempts);
    }

    /**
     * Send a request to get a resource. Both input and output stream are closed
     * when the result is fully read.
     * 
     * @param urlRessource
     *            The URL to load.
     * @param out
     *            The stream to write the URL result.
     * @param timeOut
     *            The timeout to use when trying to connect to the resource.
     * @param attempts
     *            Attempts number before throwing an exception.
     * @throws IOException
     *             If an error happen while loading data, if there is more than
     *             <code>attempts</code> timeouts.
     */
    public static void getRessource(URL urlRessource, OutputStream out, int timeOut, int attempts) throws IOException {

        int tentativesEff = 0;
        boolean recupOk = false;
        while (!recupOk && tentativesEff < attempts) {
            URLConnection connection = urlRessource.openConnection(proxy);
            connection.setRequestProperty("User-Agent", "Lynx");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

            if (properties != null) {
                for (String cle : properties.stringPropertyNames()) {
                    connection.setRequestProperty(cle, properties.getProperty(cle));
                }
            }

            connection.setReadTimeout(timeOut);

            InputStream in;
            if ("gzip".equals(connection.getContentEncoding())) {
                in = new GZIPInputStream(connection.getInputStream());
            } else if ("deflate".equals(connection.getContentEncoding())) {
                in = new DeflaterInputStream(connection.getInputStream());
            } else {
                in = connection.getInputStream();
            }

            try {
                getRessource(in, out);
                recupOk = true;
            } catch (SocketTimeoutException e) {
                LOGGER.log(Level.WARNING, String.format("Timeout when loading %s.", urlRessource), e);
                tentativesEff++;
            }

            in.close();
            out.close();
        }

        if (tentativesEff == attempts) {
            throw new IOException(String.format("Too much timeout when loading %s.", urlRessource));
        }
    }

    /**
     * Read a stream and get a byte array of the read data.
     * 
     * @param in
     *            The input stream to read.
     * @return Data read in the stream.
     * @throws IOException
     *             If an error happen while reading.
     */
    public static byte[] getRessource(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        getRessource(in, out);

        return out.toByteArray();
    }

    /**
     * Read a stream and write it simultaneously in another stream.<br>
     * If the method end fine, both streams are closed at the end.
     * 
     * @param in
     *            The input stream to read.
     * @param out
     *            The output stream to write the read data.
     * @throws IOException
     *             If an error occur while reading/writing.
     */
    public static void getRessource(InputStream in, OutputStream out) throws IOException {
        int lus;
        byte[] tampon = new byte[DEFAULT_BUFFER_SIZE];

        do {
            lus = in.read(tampon);
            if (lus != -1)
                out.write(tampon, 0, lus);
        } while (lus != -1);

        in.close();
        out.close();
    }
}
