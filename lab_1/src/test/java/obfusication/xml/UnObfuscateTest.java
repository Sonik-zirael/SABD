package obfusication.xml;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static obfusication.xml.Constants.inputStreamToString;
import static org.junit.Assert.assertEquals;

public class UnObfuscateTest {
    @Test
    public void testReadFile() throws IOException {
        UnObfuscate o = new UnObfuscate();
        o.unobfuscate("src/test/resources/test_write.txt", "src/test/resources/write_final.txt");
        File file = new File("src/test/resources/write_final.txt");
        String xml = inputStreamToString(new FileInputStream(file));
        assertEquals("<employees>  <employee id=\"111\">    <firstName>Lokesh</firstName>    <lastName>Gupta</lastName>    <location>India</location>  </employee></employees>", xml);
    }

    @Test
    public void  testFileNotFoubd() throws IOException {
        Obfuscate o = new Obfuscate();
        o.obfuscate("src/test/resources/test_not_found.txt", "src/test/resources/write_final_not_found.txt");
        File file = new File("src/test/resources/write_final_not_found.txt");
        String xml = inputStreamToString(new FileInputStream(file));
        assertEquals("", xml);
    }

}