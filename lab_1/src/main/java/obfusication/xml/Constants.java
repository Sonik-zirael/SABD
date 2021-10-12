package obfusication.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Constants {
    public final static String FILE_PATH = "initial.xml";
    public final static String SOURCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>=\" /";
    public final static String TARGET = "QKALZWSJXEDCORFVTPGBYMHNUI0243156789+=-\\ @";

    public static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
