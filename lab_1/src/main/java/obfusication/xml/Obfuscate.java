package obfusication.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import static obfusication.xml.Constants.*;

public class Obfuscate {

    public void obfuscate(String fileName, String fileToWrite) {
        String xml = readFromFile(fileName);
        StringBuilder res = new StringBuilder();
        int counter = 0;
        for (Character c : xml.toCharArray()) {

            res.append(encodeSymbol(c));
            counter++;
            if (counter % 7 == 0) {
                res.append(" ");
            }
            if (counter % 21 == 0) {
                res.append("\n");
            }
        }
        writeToFile(res.toString(), fileToWrite);

    }

    private Character encodeSymbol(Character c) {
        int index = SOURCE.toLowerCase(Locale.ROOT).indexOf(c.toString().toLowerCase(Locale.ROOT));
        if (c.toString().toLowerCase(Locale.ROOT).equals(c.toString())) {
            return TARGET.charAt(index);
        } else {
            return TARGET.toLowerCase(Locale.ROOT).charAt(index);
        }
    }

    private void writeToFile(String res, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(res);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String readFromFile(String filaName) {
        String xml = "";
        try {
            File file = new File(filaName);
            xml = inputStreamToString(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml.replaceAll("(\\s){2,}", "");
    }
}
