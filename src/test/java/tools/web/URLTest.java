package tools.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.junit.Test;

public class URLTest {

    @Test
    public void testEncode() {

        CharsetDecoder decode = Charset.defaultCharset().newDecoder();

        assertEquals("coucou%20c%27est%20moi", URLUtil.encode("coucou c'est moi"));
        try {
            assertEquals("And%20another%20one%20with%20%26%2A%27%22%C3%A7%C3%A9%20chars.",
                    URLUtil.encode(
                            new String(decode.decode(ByteBuffer.wrap("And another one with &*'\"çé chars.".getBytes()))
                                    .toString().getBytes(), "UTF-8")));
        } catch (UnsupportedEncodingException | CharacterCodingException e) {
            fail(e.getMessage());
        }
    }
}
