package tools.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class containing URL related utility methods.
 * 
 * @author Alexandre CANDIAN
 *
 */
public class URLUtil {

    private static final Logger LOGGER = Logger.getLogger(URLUtil.class.getName());

    private URLUtil() {
    }

    /**
     * Encode a string to URL format.<br>
     * The Java method supposed to do the job seems to not follow the standard.
     * 
     * @param value
     *            The string to encode.
     * @return The encoded string or an empty string if an error occur.
     */
    public static String encode(String value) {
        StringBuilder buf = new StringBuilder();
        try {
            String encoded = URLEncoder.encode(value, "UTF-8");

            char focus;
            int i = 0;
            while (i < encoded.length()) {
                focus = encoded.charAt(i);
                if (focus == '*') {
                    buf.append("%2A");
                } else if (focus == '+') {
                    buf.append("%20");
                } else if (focus == '%' && (i + 1) < encoded.length() && encoded.charAt(i + 1) == '7'
                        && encoded.charAt(i + 2) == 'E') {
                    buf.append('~');
                    i += 2;
                } else {
                    buf.append(focus);
                }
                i++;
            }
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return buf.toString();
    }
}
