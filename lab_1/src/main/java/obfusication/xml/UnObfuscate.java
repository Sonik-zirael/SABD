package obfusication.xml;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import static obfusication.xml.Constants.*;

public class UnObfuscate {
    public void unobfuscate(String readFrom, String writeTo) {
        XmlMapper xmlMapper = new XmlMapper();
        EmployeesCollection collection;
        String res = "";
        String xml = readFromFile(readFrom);
        int counter = 0;
        for (Character c : xml.toCharArray()) {
            res += decodeSymbol(c);
            counter++;
            if (counter % 8 == 0) {
                res = removeLastCharOptional(res);
            }
        }

        try {
            collection = xmlMapper.readValue(res, EmployeesCollection.class);
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.writeValue(new File(writeTo), collection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(String fileName) {
        String str = "";
        try {
            File file = new File(fileName);
            str = inputStreamToString(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }

    private Character decodeSymbol(Character c) {
        int index = TARGET.toLowerCase(Locale.ROOT).indexOf(c.toString().toLowerCase(Locale.ROOT));
        if (c.toString().toLowerCase(Locale.ROOT).equals(c.toString())) {
            return SOURCE.charAt(index);
        } else {
            return SOURCE.toLowerCase(Locale.ROOT).charAt(index);
        }
    }
}
