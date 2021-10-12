package obfusication.xml;

import static obfusication.xml.Constants.FILE_PATH;

public class Main {
    public static void main(String[] args) {
        String mode = System.getenv("MODE");
        if (mode.equals("1")) {
            Obfuscate obf = new Obfuscate();
            obf.obfuscate(FILE_PATH, "obfuscated.xml");
            System.out.println("Ready. Please check obfuscated.xml");
        } else {
            UnObfuscate unofs = new UnObfuscate();
            unofs.unobfuscate("obfuscated.xml", "final.xml");
            System.out.println("Ready. Please check final.xml");
        }
    }
}
