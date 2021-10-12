package obfusication.xml;


import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static obfusication.xml.Constants.inputStreamToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ObfuscateTest {
    @Test
    public void whenThis_thenThat() {
        assertTrue(true);
    }

    @Test
    public void testReadFile() throws IOException {
        Obfuscate o = new Obfuscate();
        o.obfuscate("src/test/resources/test_read.txt", "src/test/resources/test_write.txt");
        File file = new File("src/test/resources/test_write.txt");
        String xml = inputStreamToString(new FileInputStream(file));
        assertEquals("+ZOVCFU ZZG=+ZO VCFUZZ  XL-\\222 \\=+WXPG BrQOZ=c FDZGJ+@ WXPGBrQ OZ=+CQG BrQOZ=s YVBQ+@C QGBrQOZ =+CFAQB XFR=xRL XQ+@CFA QBXFR=+ @ZOVCFU ZZ=+@ZO VCFUZZG =", xml);
    }

    @Test
    public void  testFileNotFoubd() throws IOException {
        Obfuscate o = new Obfuscate();
        o.obfuscate("src/test/resources/test_not_found.txt", "src/test/resources/test_write_not_found.txt");
        File file = new File("src/test/resources/test_write_not_found.txt");
        String xml = inputStreamToString(new FileInputStream(file));
        assertEquals("", xml);
    }
}