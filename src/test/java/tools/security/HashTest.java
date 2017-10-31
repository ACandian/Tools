package tools.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class HashTest {

    private static final String CHAINE_VIDE = "";
    private static final String CHAINE_SIMPLE = "premiereChaine";
    private static final String CHAINE_CARACTERES_SPECIAUX = "Une autre chaîne avec des caractères accentués.";

    @Test
    public void testPrivateConstructor() throws NoSuchMethodException, SecurityException {
        Constructor<?> constructors[] = Hash.class.getDeclaredConstructors();

        for (Constructor<?> c : constructors) {
            assertTrue("All constructors of an utility class should be private.", Modifier.isPrivate(c.getModifiers()));
        }
    }

    @Test
    public void testBytesToHex() {
        assertEquals("80", Hash.bytesToHex(new byte[] { Byte.MIN_VALUE }));

        assertEquals("FF", Hash.bytesToHex(new byte[] { -1 }));

        assertEquals("00", Hash.bytesToHex(new byte[] { 0 }));

        assertEquals("01", Hash.bytesToHex(new byte[] { 1 }));

        assertEquals("7F", Hash.bytesToHex(new byte[] { Byte.MAX_VALUE }));
    }

    @Test
    public void testHashMD5() {

        try {
            assertEquals("483ecff07da6309d7d0d53a9283b9c12".toUpperCase(),
                    Hash.bytesToHex(Hash.hash(CHAINE_SIMPLE.getBytes(), "MD5")));

            assertEquals("d41d8cd98f00b204e9800998ecf8427e".toUpperCase(),
                    Hash.bytesToHex(Hash.hash(CHAINE_VIDE.getBytes(), "MD5")));

            assertEquals("c7bb1f0d1c24b0128ef7a5b53b78d566".toUpperCase(),
                    Hash.bytesToHex(Hash.hash(CHAINE_CARACTERES_SPECIAUX.getBytes(), "MD5")));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testHashSHA1() {

        try {
            assertEquals("0e62780d7f0eacdc222a1b507ca62b3c2edfe451".toUpperCase(),
                    Hash.bytesToHex(Hash.hash(CHAINE_SIMPLE.getBytes(), "SHA-1")));

            assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709".toUpperCase(),
                    Hash.bytesToHex(Hash.hash(CHAINE_VIDE.getBytes(), "SHA-1")));

            assertEquals("926c9a1adf8a91055b44f46f94a8d3b1c422be6e".toUpperCase(),
                    Hash.bytesToHex(Hash.hash(CHAINE_CARACTERES_SPECIAUX.getBytes(), "SHA-1")));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }
}
